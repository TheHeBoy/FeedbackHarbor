package cn.hh.harbor.module.system.api.token;

import cn.hh.harbor.module.system.api.token.dto.TokenRespDTO;
import cn.hh.harbor.module.system.api.token.dto.TokenCheckRespDTO;
import cn.hh.harbor.module.system.api.token.dto.TokenCreateReqDTO;
import cn.hh.harbor.module.system.convert.token.TokenConvert;
import cn.hh.harbor.module.system.dal.dataobject.token.TokenAccessDO;
import cn.hh.harbor.module.system.service.token.TokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * Token API 实现类
 */
@Service
public class TokenApiImpl implements TokenApi {

    @Resource
    private TokenService tokenService;

    @Override
    public TokenRespDTO createAccessToken(TokenCreateReqDTO reqDTO, boolean isRequireRefreshToken) {
        TokenAccessDO accessTokenDO = tokenService.createAccessToken(reqDTO.getUserId(), reqDTO.getUserType(),
                Collections.singletonList(reqDTO.getTenantId()), isRequireRefreshToken);
        return TokenConvert.INSTANCE.convert2(accessTokenDO);
    }

    @Override
    public TokenCheckRespDTO checkAccessToken(String accessToken) {
        return TokenConvert.INSTANCE.convert(tokenService.checkAccessToken(accessToken));
    }

    @Override
    public TokenRespDTO removeAccessToken(String accessToken) {
        TokenAccessDO accessTokenDO = tokenService.removeAccessToken(accessToken);
        return TokenConvert.INSTANCE.convert2(accessTokenDO);
    }

    @Override
    public TokenRespDTO refreshAccessToken(String refreshToken) {
        TokenAccessDO accessTokenDO = tokenService.refreshAccessToken(refreshToken);
        return TokenConvert.INSTANCE.convert2(accessTokenDO);
    }

}
