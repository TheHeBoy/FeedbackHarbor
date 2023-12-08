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
     * 校验访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    TokenCheckRespDTO checkAccessToken(String accessToken);

}
