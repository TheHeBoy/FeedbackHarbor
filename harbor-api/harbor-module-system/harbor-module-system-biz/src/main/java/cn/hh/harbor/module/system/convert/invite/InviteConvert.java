package cn.hh.harbor.module.system.convert.invite;

import cn.hh.harbor.module.system.controller.admin.invite.vo.InviteReplyListRespVO;
import cn.hh.harbor.module.system.dal.dataobject.invite.InviteDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface InviteConvert {

    InviteConvert INSTANCE = Mappers.getMapper(InviteConvert.class);

    List<InviteReplyListRespVO> convert(List<InviteDO> beans);
}
