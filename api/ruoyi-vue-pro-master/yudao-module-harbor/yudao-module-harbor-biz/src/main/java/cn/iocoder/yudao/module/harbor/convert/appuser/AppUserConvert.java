package cn.iocoder.yudao.module.harbor.convert.appuser;

import cn.iocoder.yudao.module.harbor.api.appuser.dto.AppUserRespDTO;
import cn.iocoder.yudao.module.harbor.controller.app.appuser.vo.AppUserCreateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;

/**
 * App用户 Convert
 *
 *  hehong
 */
@Mapper
public interface AppUserConvert {

    AppUserConvert INSTANCE = Mappers.getMapper(AppUserConvert.class);

    AppUserDO convert(AppUserCreateReqVO bean);

    AppUserRespDTO convertApi(AppUserCreateReqVO appUserDO);
    AppUserRespDTO convertApi(AppUserDO appUserDO);
}
