package cn.hh.harbor.module.system.api.token;

import cn.hh.harbor.module.system.api.token.dto.TokenRespDTO;
import cn.hh.harbor.module.system.api.token.dto.TokenCheckRespDTO;
import cn.hh.harbor.module.system.api.token.dto.TokenCreateReqDTO;

import javax.validation.Valid;

/**
 * Token API 接口
 */
public interface TokenApi {

    /**
     * 创建访问令牌
     *
     * @param reqDTO              访问令牌的创建信息
     * @param isRequireRefreshToken 是否需要刷新 token
     * @return 访问令牌的信息
     */
    TokenRespDTO createAccessToken(@Valid TokenCreateReqDTO reqDTO, boolean isRequireRefreshToken);

    /**
     * 校验访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    TokenCheckRespDTO checkAccessToken(String accessToken);

    /**
     * 移除访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    TokenRespDTO removeAccessToken(String accessToken);

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 访问令牌的信息
     */
    TokenRespDTO refreshAccessToken(String refreshToken);
}
