package cn.iocoder.yudao.module.uservoice.service.auth;

import cn.iocoder.yudao.module.uservoice.controller.app.auth.vo.AppAuthLoginReqVO;
import cn.iocoder.yudao.module.uservoice.controller.app.auth.vo.AppAuthLoginRespVO;

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
}
