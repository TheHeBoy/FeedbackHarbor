package cn.hh.harbor.module.system.convert.user;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.module.system.api.user.dto.UserRespDTO;
import cn.hh.harbor.module.system.controller.admin.user.vo.profile.UserProfileRespVO;
import cn.hh.harbor.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import cn.hh.harbor.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import cn.hh.harbor.module.system.controller.admin.user.vo.team.UserTeamListRespVO;
import cn.hh.harbor.module.system.controller.admin.user.vo.team.UserTeamRespVO;
import cn.hh.harbor.module.system.controller.admin.user.vo.user.*;
import cn.hh.harbor.module.system.dal.dataobject.permission.RoleDO;
import cn.hh.harbor.module.system.dal.dataobject.user.UserDO;
import cn.hh.harbor.module.system.service.user.vo.UserCreateSocialReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

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

    PageResult<UserRespVO> convert(PageResult<UserDO> userPage);

    UserRespVO convert(UserDO bean);

    UserTeamListRespVO convertTeam(UserDO bean);

    List<UserTeamListRespVO> convert(List<UserDO> bean);

    List<UserTeamRespVO> convertTeam(List<UserDO> bean);
}
