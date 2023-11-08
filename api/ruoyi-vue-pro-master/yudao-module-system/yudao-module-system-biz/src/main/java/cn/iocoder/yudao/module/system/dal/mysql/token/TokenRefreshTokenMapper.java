package cn.iocoder.yudao.module.system.dal.mysql.token;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.token.TokenRefreshDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenRefreshTokenMapper extends BaseMapperX<TokenRefreshDO> {

    default int deleteByRefreshToken(String refreshToken) {
        return delete(new LambdaQueryWrapperX<TokenRefreshDO>()
                .eq(TokenRefreshDO::getRefreshToken, refreshToken));
    }

    default TokenRefreshDO selectByRefreshToken(String refreshToken) {
        return selectOne(TokenRefreshDO::getRefreshToken, refreshToken);
    }

}
