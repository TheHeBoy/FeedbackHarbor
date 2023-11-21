package cn.hh.harbor.module.system.convert.token;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.module.system.api.token.dto.TokenRespDTO;
import cn.hh.harbor.module.system.api.token.dto.TokenCheckRespDTO;
import cn.hh.harbor.module.system.controller.admin.token.vo.AccessTokenRespVO;
import cn.hh.harbor.module.system.dal.dataobject.token.TokenAccessDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TokenConvert {

    TokenConvert INSTANCE = Mappers.getMapper(TokenConvert.class);

    TokenCheckRespDTO convert(TokenAccessDO bean);

    PageResult<AccessTokenRespVO> convert(PageResult<TokenAccessDO> page);

    TokenRespDTO convert2(TokenAccessDO bean);

}
