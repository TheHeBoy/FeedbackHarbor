package cn.hh.harbor.module.system.service.auth;

import cn.hh.harbor.framework.common.enums.UserTypeEnum;
import cn.hh.harbor.module.system.controller.admin.auth.vo.*;
import cn.hh.harbor.module.system.dal.dataobject.token.TokenAccessDO;
import cn.hh.harbor.module.system.enums.social.SocialTypeEnum;

import javax.validation.Valid;

/**
 * 认证 Service 接口
 * 提供用户的登录、登出的能力
 */
public interface AuthService {

    /**
     * 账号登录
     *
     * @param username     用户名
     * @param password     密码
     * @param userTypeEnum 用户类型
     * @return 登录结果
     */
    TokenAccessDO login(String username, String password, UserTypeEnum userTypeEnum);

    /**
     * 基于 token 退出登录
     *
     * @param token   token
     * @param logType 登出类型
     */
    void logout(String token, Integer logType);

    /**
     * 社交快捷登录，使用 code 授权码
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    TokenAccessDO socialLogin(@Valid AuthSocialLoginReqVO reqVO, UserTypeEnum userTypeEnum);

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 登录结果
     */
    AuthLoginRespVO refreshToken(String refreshToken);

    /**
     * 获得社交平台的授权 URL
     *
     * @param type        社交平台的类型 {@link SocialTypeEnum}
     * @param redirectUri 重定向 URL
     * @return 社交平台的授权 URL
     */
    String getAuthorizeUrl(Integer type, String redirectUri);

    /**
     * 邮箱注册
     * @param reqVO 注册信息
     */
    void mailRegister(AuthMailRegisterReqVO reqVO);

    /**
     * 重置密码
     * @param reqVO 重置信息
     */
    void resetPasswd(AuthResetPasswdReqVO reqVO);
}
