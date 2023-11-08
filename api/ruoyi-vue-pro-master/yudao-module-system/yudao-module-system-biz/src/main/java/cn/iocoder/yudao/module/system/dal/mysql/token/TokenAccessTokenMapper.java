package cn.iocoder.yudao.module.system.dal.mysql.token;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.token.vo.AccessTokenPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.token.TokenAccessDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface TokenAccessTokenMapper extends BaseMapperX<TokenAccessDO> {

    default TokenAccessDO selectByAccessToken(String accessToken) {
        return selectOne(TokenAccessDO::getAccessToken, accessToken);
    }

    default TokenAccessDO selectByRefreshToken(String refreshToken) {
        return selectOne(TokenAccessDO::getRefreshToken, refreshToken);
    }

    default PageResult<TokenAccessDO> selectPage(AccessTokenPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TokenAccessDO>()
                .eqIfPresent(TokenAccessDO::getUserId, reqVO.getUserId())
                .gt(TokenAccessDO::getExpiresTime, LocalDateTime.now())
                .orderByDesc(TokenAccessDO::getId));
    }

}
