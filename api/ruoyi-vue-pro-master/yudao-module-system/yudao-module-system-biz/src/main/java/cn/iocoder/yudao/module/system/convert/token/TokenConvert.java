package cn.iocoder.yudao.module.system.convert.token;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.api.token.dto.TokenRespDTO;
import cn.iocoder.yudao.module.system.api.token.dto.TokenCheckRespDTO;
import cn.iocoder.yudao.module.system.controller.admin.token.vo.AccessTokenRespVO;
import cn.iocoder.yudao.module.system.dal.dataobject.token.TokenAccessDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TokenConvert {

    TokenConvert INSTANCE = Mappers.getMapper(TokenConvert.class);

    TokenCheckRespDTO convert(TokenAccessDO bean);

    PageResult<AccessTokenRespVO> convert(PageResult<TokenAccessDO> page);

    TokenRespDTO convert2(TokenAccessDO bean);

}
