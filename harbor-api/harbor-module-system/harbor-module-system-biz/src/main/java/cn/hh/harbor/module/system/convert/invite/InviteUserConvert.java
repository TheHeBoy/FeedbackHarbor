package cn.hh.harbor.module.system.convert.invite;

import cn.hh.harbor.module.system.controller.admin.invite.vo.user.InviteReplyListRespVO;
import cn.hh.harbor.module.system.dal.dataobject.invite.InviteUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface InviteUserConvert {

    InviteUserConvert INSTANCE = Mappers.getMapper(InviteUserConvert.class);

    List<InviteReplyListRespVO> convert(List<InviteUserDO> beans);
}
