package cn.iocoder.yudao.module.harbor.service.auth;


import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.social.core.YudaoAuthRequestFactory;
import cn.iocoder.yudao.module.harbor.controller.app.appuser.vo.AppUserCreateReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.auth.vo.AppAuthSocialLoginReqVO;
import cn.iocoder.yudao.module.harbor.enums.appuser.AppUserTypeEnum;
import cn.iocoder.yudao.module.system.api.oauth2.OAuth2TokenApi;
import cn.iocoder.yudao.module.system.api.oauth2.dto.OAuth2AccessTokenCreateReqDTO;
import cn.iocoder.yudao.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import cn.iocoder.yudao.module.system.api.social.SocialUserApi;
import cn.iocoder.yudao.module.system.enums.logger.LoginLogTypeEnum;
import cn.iocoder.yudao.module.system.enums.oauth2.OAuth2ClientConstants;
import cn.iocoder.yudao.module.harbor.controller.app.auth.vo.AppAuthLoginReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.auth.vo.AppAuthLoginRespVO;
import cn.iocoder.yudao.module.harbor.convert.auth.AuthConvert;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;
import cn.iocoder.yudao.module.harbor.service.appuser.AppUserService;
import cn.iocoder.yudao.module.system.enums.social.SocialTypeEnum;
import com.xingyuv.jushauth.model.AuthCallback;
import com.xingyuv.jushauth.model.AuthResponse;
import com.xingyuv.jushauth.model.AuthUser;
import com.xingyuv.jushauth.request.AuthRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.json.JsonUtils.toJsonString;
import static cn.iocoder.yudao.module.harbor.enums.ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS;
import static cn.iocoder.yudao.module.harbor.enums.ErrorCodeConstants.AUTH_LOGIN_USER_DISABLED;
import static cn.iocoder.yudao.module.harbor.enums.appuser.AppUserTypeEnum.THIRD_PARTY;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

@Service
@Slf4j
public class AppAuthServiceImpl implements AppAuthService {

    @Resource
    private AppUserService appUserService;

    @Resource
    private OAuth2TokenApi oauth2TokenApi;

    @Resource
    private SocialUserApi socialUserApi;

    @Resource
    private YudaoAuthRequestFactory yudaoAuthRequestFactory;

    @Override
    public AppAuthLoginRespVO login(AppAuthLoginReqVO reqVO) {
        // 使用用户名 + 密码，进行登录。
        AppUserDO user = loginUser(reqVO.getUsername(), reqVO.getPassword());

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user);
    }

    private AppUserDO loginUser(String userName, String password) {
        final AppUserDO user = appUserService.getAppUserByUserName(userName);
        //账号不存在
        if (ObjectUtil.isNull(user)) {
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        //密码错误
        if (ObjectUtil.notEqual(password, user.getPassword())) {
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    @Override
    public AppAuthLoginRespVO refreshToken(String refreshToken) {
        return AuthConvert.INSTANCE.convert(
                oauth2TokenApi.refreshAccessToken(refreshToken, OAuth2ClientConstants.CLIENT_ID_DEFAULT));
    }

    @Override
    public AppAuthLoginRespVO socialLogin(AppAuthSocialLoginReqVO reqVO) {
        AuthUser authUser = getAuthUser(reqVO.getType(), reqVO.getCode(), reqVO.getState());

        String openId = authUser.getUuid() + "-" +authUser.getSource();

        AppUserDO appUserDO = appUserService.getAppUserByUserOpenId(openId);

        Long userId;
        //用户不存在则自动创建
        if (appUserDO == null) {
            AppUserCreateReqVO createReqVO = new AppUserCreateReqVO();
            createReqVO.setAvatar(authUser.getAvatar());
            createReqVO.setNickname(authUser.getNickname());
            createReqVO.setUserOpenId(openId);
            createReqVO.setUserType(THIRD_PARTY.getCode());
            userId = appUserService.createAppUser(createReqVO);
        } else {
            userId = appUserDO.getId();
        }

        // 自动登录
        AppUserDO user = appUserService.getUser(userId);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user);
    }

    @Override
    public String getAuthorizeUrl(Integer type, String redirectUri) {
        return socialUserApi.getAuthorizeUrl(type, redirectUri);
    }

    private AppAuthLoginRespVO createTokenAfterLoginSuccess(AppUserDO user) {
        // 创建 Token 令牌
        OAuth2AccessTokenRespDTO accessTokenRespDTO = oauth2TokenApi.createAccessToken(
                new OAuth2AccessTokenCreateReqDTO()
                        .setUserId(user.getId())
                        .setUserType(getUserType().getValue())
                        .setClientId(OAuth2ClientConstants.CLIENT_ID_DEFAULT));
        // 构建返回结果
        return AuthConvert.INSTANCE.convert(accessTokenRespDTO);
    }

    @Override
    public void logout(String token) {
        // 删除访问令牌
        oauth2TokenApi.removeAccessToken(token);
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.MEMBER;
    }

    private AuthUser getAuthUser(Integer type, String code, String state) {
        AuthRequest authRequest = yudaoAuthRequestFactory.get(SocialTypeEnum.valueOfType(type).getSource());
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
