package cn.iocoder.yudao.module.harbor.service.auth;


import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.module.system.api.oauth2.OAuth2TokenApi;
import cn.iocoder.yudao.module.system.api.oauth2.dto.OAuth2AccessTokenCreateReqDTO;
import cn.iocoder.yudao.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import cn.iocoder.yudao.module.system.enums.oauth2.OAuth2ClientConstants;
import cn.iocoder.yudao.module.harbor.controller.app.auth.vo.AppAuthLoginReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.auth.vo.AppAuthLoginRespVO;
import cn.iocoder.yudao.module.harbor.convert.auth.AuthConvert;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;
import cn.iocoder.yudao.module.harbor.service.appuser.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.harbor.enums.ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS;
import static cn.iocoder.yudao.module.harbor.enums.ErrorCodeConstants.AUTH_LOGIN_USER_DISABLED;

@Service
@Slf4j
public class AppAuthServiceImpl implements AppAuthService {

    @Resource
    private AppUserService appUserService;

    @Resource
    private OAuth2TokenApi oauth2TokenApi;

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

    private UserTypeEnum getUserType() {
        return UserTypeEnum.MEMBER;
    }

    @Override
    public void logout(String token) {
        // 删除访问令牌
        oauth2TokenApi.removeAccessToken(token);
    }
}
