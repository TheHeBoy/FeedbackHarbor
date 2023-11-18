package cn.iocoder.yudao.module.system.convert.user;

import cn.iocoder.yudao.module.system.api.user.dto.UserRespDTO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.profile.UserProfileRespVO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.*;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.UserDO;
import cn.iocoder.yudao.module.system.service.user.vo.UserCreateSocialReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserPageItemRespVO convert(UserDO bean);

    UserDO convert(UserCreateReqVO bean);

    UserDO convert(UserUpdateReqVO bean);

    UserExcelVO convert02(UserDO bean);

    UserDO convert(UserImportExcelVO bean);

    UserProfileRespVO convert03(UserDO bean);

    List<UserProfileRespVO.Role> convertList(List<RoleDO> list);

    UserDO convert(UserProfileUpdateReqVO bean);

    UserDO convert(UserProfileUpdatePasswordReqVO bean);

    List<UserSimpleRespVO> convertList04(List<UserDO> list);

    UserRespDTO convert4(UserDO bean);
    UserLoginInfoRespVO convert5(UserDO bean);

    List<UserRespDTO> convertList4(List<UserDO> users);

    UserDO convert(UserCreateSocialReqVO reqVO);
}
