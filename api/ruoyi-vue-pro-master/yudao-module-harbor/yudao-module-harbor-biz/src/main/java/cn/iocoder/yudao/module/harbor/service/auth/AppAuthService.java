package cn.iocoder.yudao.module.harbor.service.auth;

import cn.iocoder.yudao.module.harbor.controller.app.auth.vo.AppAuthLoginReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.auth.vo.AppAuthLoginRespVO;
import cn.iocoder.yudao.module.harbor.controller.app.auth.vo.AppAuthSocialLoginReqVO;

import javax.validation.Valid;

/**
 *
 * 提供用户的账号密码登录、token 的校验等认证相关的功能
 *
 *
 */
public interface AppAuthService {

    /**
     * 账号 + 密码登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AppAuthLoginRespVO login(@Valid AppAuthLoginReqVO reqVO);

    /**
     * 基于 token 退出登录
     *
     * @param token token
     */
    void logout(String token);

    /**
     * 社交快捷登录，使用 code 授权码
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AppAuthLoginRespVO socialLogin(@Valid AppAuthSocialLoginReqVO reqVO);


    /**
     * 获得社交认证 URL
     *
     * @param type 社交平台类型
     * @param redirectUri 跳转地址
     * @return 认证 URL
     */
    String getAuthorizeUrl(Integer type, String redirectUri);
}
