package cn.hh.harbor.module.system.service.auth;

import cn.hh.harbor.framework.common.enums.CommonStatusEnum;
import cn.hh.harbor.framework.common.util.http.HttpUtils;
import cn.hh.harbor.framework.common.util.monitor.TracerUtils;
import cn.hh.harbor.framework.common.util.servlet.ServletUtils;
import cn.hh.harbor.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.hh.harbor.module.system.controller.admin.auth.vo.AuthMailRegisterReqVO;
import cn.hh.harbor.module.system.controller.admin.auth.vo.AuthResetPasswdReqVO;
import cn.hh.harbor.module.system.controller.admin.auth.vo.AuthSocialLoginReqVO;
import cn.hh.harbor.module.system.controller.admin.user.vo.user.UserCreateReqVO;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantUserDO;
import cn.hh.harbor.module.system.dal.dataobject.token.TokenAccessDO;
import cn.hh.harbor.module.system.dal.dataobject.user.UserDO;
import cn.hh.harbor.module.system.dal.mysql.tenant.TenantUserMapper;
import cn.hh.harbor.module.system.enums.logger.LoginLogTypeEnum;
import cn.hh.harbor.module.system.enums.logger.LoginResultEnum;
import cn.hh.harbor.module.system.enums.mail.MailCaptchaSceneEnum;
import cn.hh.harbor.module.system.enums.social.SocialTypeEnum;
import cn.hh.harbor.module.system.service.logger.LoginLogService;
import cn.hh.harbor.module.system.service.mail.MailCaptchaService;
import cn.hh.harbor.module.system.service.mail.vo.MailCaptchaUseReqVO;
import cn.hh.harbor.module.system.service.social.SocialService;
import cn.hh.harbor.module.system.service.token.TokenService;
import cn.hh.harbor.module.system.service.user.UserService;
import cn.hh.harbor.module.system.service.user.vo.UserCreateSocialReqVO;
import cn.hutool.core.util.ObjectUtil;
import com.xingyuv.jushauth.model.AuthCallback;
import com.xingyuv.jushauth.model.AuthResponse;
import com.xingyuv.jushauth.model.AuthUser;
import com.xingyuv.jushauth.request.AuthRequest;
import com.xingyuv.jushauth.utils.AuthStateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.hh.harbor.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.hh.harbor.framework.common.util.json.JsonUtils.toJsonString;
import static cn.hh.harbor.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.hh.harbor.module.system.enums.ErrorCodeConstants.*;

/**
 * Auth Service 实现类
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Resource
    private SocialService socialService;
    @Resource
    private UserService userService;
    @Resource
    private LoginLogService loginLogService;
    @Resource
    private TokenService tokenService;
    @Resource
    private TenantUserMapper tenantUserMapper;
    @Resource
    private MailCaptchaService mailCaptchaService;

    @Override
    public TokenAccessDO login(String username, String password) {
        // 使用账号密码，进行登录
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        UserDO user = userService.getUserByUsername(username);
        if (user == null) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), username, LoginLogTypeEnum.LOGIN_USERNAME);
    }

    @Override
    public TokenAccessDO socialLogin(AuthSocialLoginReqVO reqVO) {
        AuthUser authUser = getAuthUser(reqVO.getType(), reqVO.getCode(), reqVO.getState(), reqVO.getRedirectUri());
        UserDO appUserDO = userService.getUserByOpenIdAndSocialType(authUser.getUuid(), reqVO.getType());
        //用户不存在则自动创建
        Long userId;
        if (appUserDO == null) {
            userId = userService.createSocialUser(
                    UserCreateSocialReqVO.builder()
                            .nickname(authUser.getNickname())
                            .avatar(authUser.getAvatar())
                            .socialOpenId(authUser.getUuid())
                            .socialType(reqVO.getType())
                            .build());
        } else {
            userId = appUserDO.getId();
        }

        // 自动登录
        UserDO user = userService.getUser(userId);
        // 创建 Token 令牌
        return createTokenAfterLoginSuccess(user.getId(), user.getUsername(), LoginLogTypeEnum.LOGIN_SOCIAL);
    }

    @Override
    public void logout(String token, Integer logType) {
        // 删除访问令牌
        TokenAccessDO accessTokenDO = tokenService.removeAccessToken(token);
        if (accessTokenDO == null) {
            return;
        }
        // 删除成功，则记录登出日志
        createLogoutLog(accessTokenDO.getUserId(), logType);
    }

    @Override
    public String getAuthorizeUrl(Integer type, String redirectUri) {
        // 获得对应的 AuthRequest 实现
        AuthRequest authRequest = socialService.getAuthRequest(SocialTypeEnum.valueOfType(type), redirectUri);
        // 生成跳转地址
        String authorizeUri = authRequest.authorize(AuthStateUtils.createState());
        return HttpUtils.replaceUrlQuery(authorizeUri, "redirect_uri", redirectUri);
    }

    @Override
    public void mailRegister(AuthMailRegisterReqVO reqVO) {
        // 校验验证码
        mailCaptchaService.useMailCaptcha(new MailCaptchaUseReqVO()
                .setUsedIp(getClientIP())
                .setMail(reqVO.getMail())
                .setCaptcha(reqVO.getCaptcha())
                .setScene(MailCaptchaSceneEnum.REGISTER.getScene()));

        // 插入用户
        UserCreateReqVO userCreateReqVO = new UserCreateReqVO();
        userCreateReqVO.setEmail(reqVO.getMail());
        userCreateReqVO.setUsername(reqVO.getUsername());
        userCreateReqVO.setPassword(reqVO.getPassword());
        userService.createUser(userCreateReqVO);
    }

    @Override
    public void resetPasswd(AuthResetPasswdReqVO reqVO) {
        // 校验验证码
        mailCaptchaService.useMailCaptcha(new MailCaptchaUseReqVO()
                .setUsedIp(getClientIP())
                .setMail(reqVO.getMail())
                .setCaptcha(reqVO.getCaptcha())
                .setScene(MailCaptchaSceneEnum.RESET_PASSWD.getScene()));

        userService.updateUserPassword(reqVO.getUserId(), reqVO.getPassword());
    }

    private TokenAccessDO createTokenAfterLoginSuccess(Long userId, String username, LoginLogTypeEnum logType) {
        // 插入登陆日志
        createLoginLog(userId, username, logType, LoginResultEnum.SUCCESS);
        // 当前用户拥有的社区租户
        List<TenantUserDO> tenantUserDOS = tenantUserMapper.selectList(TenantUserDO::getUserId, userId);
        // 创建访问令牌
        return tokenService.createAccessToken(userId,
                tenantUserDOS.stream().map(TenantUserDO::getTenantId).collect(Collectors.toList()));
    }

    private void createLogoutLog(Long userId, Integer logType) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType);
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUsername(getUsername(userId));
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        loginLogService.createLoginLog(reqDTO);
    }

    private void createLoginLog(Long userId, String username, LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        // 插入登录日志
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logTypeEnum.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUsername(username);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogService.createLoginLog(reqDTO);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }

    private String getUsername(Long userId) {
        if (userId == null) {
            return null;
        }
        UserDO user = userService.getUser(userId);
        return user != null ? user.getUsername() : null;
    }


    /**
     * 得到社交登录的用户信息
     *
     * @param type        社交应用类型
     * @param code        授权码
     * @param state       状态码
     * @param redirectUri 回调地址
     * @return {@link AuthUser}
     */
    private AuthUser getAuthUser(Integer type, String code, String state, String redirectUri) {
        AuthRequest authRequest = socialService.getAuthRequest(SocialTypeEnum.valueOfType(type), redirectUri);
        AuthCallback authCallback = AuthCallback.builder().code(code).state(state).build();
        AuthResponse<?> authResponse = authRequest.login(authCallback);
        log.info("[getAuthUser][请求社交平台 type({}) request({}) response({})]", type,
                toJsonString(authCallback), toJsonString(authResponse));
        if (!authResponse.ok()) {
            throw exception(SOCIAL_USER_AUTH_FAILURE, authResponse.getMsg());
        }
        return (AuthUser) authResponse.getData();
    }
}
