package cn.hh.harbor.module.system.dal.mysql.token;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.framework.mybatis.core.mapper.BaseMapperX;
import cn.hh.harbor.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.hh.harbor.module.system.controller.admin.token.vo.AccessTokenPageReqVO;
import cn.hh.harbor.module.system.dal.dataobject.token.TokenAccessDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface TokenAccessTokenMapper extends BaseMapperX<TokenAccessDO> {

    default TokenAccessDO selectByAccessToken(String accessToken) {
        return selectOne(TokenAccessDO::getAccessToken, accessToken);
    }

    default PageResult<TokenAccessDO> selectPage(AccessTokenPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TokenAccessDO>()
                .eqIfPresent(TokenAccessDO::getUserId, reqVO.getUserId())
                .gt(TokenAccessDO::getExpiresTime, LocalDateTime.now())
                .orderByDesc(TokenAccessDO::getId));
    }

}
