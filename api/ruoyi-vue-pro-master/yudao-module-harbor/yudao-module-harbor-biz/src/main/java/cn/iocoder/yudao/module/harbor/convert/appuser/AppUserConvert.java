package cn.iocoder.yudao.module.harbor.convert.appuser;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.harbor.controller.admin.appuser.vo.*;
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

    AppUserDO convert(AppUserUpdateReqVO bean);

    AppUserRespVO convert(AppUserDO bean);

    List<AppUserRespVO> convertList(List<AppUserDO> list);

    PageResult<AppUserRespVO> convertPage(PageResult<AppUserDO> page);

    List<AppUserExcelVO> convertList02(List<AppUserDO> list);

}
