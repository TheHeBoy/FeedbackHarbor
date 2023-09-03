package cn.iocoder.yudao.module.uservoice.convert.auth;


import cn.iocoder.yudao.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import cn.iocoder.yudao.module.uservoice.controller.app.auth.vo.AppAuthLoginRespVO;
import cn.iocoder.yudao.module.uservoice.controller.app.auth.vo.AppAuthUserInfoRespVO;
import cn.iocoder.yudao.module.uservoice.dal.dataobject.appuser.AppUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    AppAuthLoginRespVO convert(OAuth2AccessTokenRespDTO bean);

    AppAuthUserInfoRespVO convert(AppUserDO user);
}
