package cn.iocoder.yudao.module.harbor.convert.auth;


import cn.iocoder.yudao.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import cn.iocoder.yudao.module.harbor.controller.app.auth.vo.AppAuthLoginRespVO;
import cn.iocoder.yudao.module.harbor.controller.app.auth.vo.AppAuthUserInfoRespVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    AppAuthLoginRespVO convert(OAuth2AccessTokenRespDTO bean);

    AppAuthUserInfoRespVO convert(AppUserDO user);
}
