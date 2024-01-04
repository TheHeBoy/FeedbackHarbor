package cn.hh.harbor.module.system.dal.mysql.invite;

import cn.hh.harbor.framework.mybatis.core.mapper.BaseMapperX;
import cn.hh.harbor.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.hh.harbor.module.system.dal.dataobject.invite.InviteUserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InviteUserMapper extends BaseMapperX<InviteUserDO> {

    default InviteUserDO selectOneByInviteAndStatus(Long inviteeUserId, Long inviterUserId, List<Integer> statusList) {
        return selectOne(new LambdaQueryWrapperX<InviteUserDO>()
                .eq(InviteUserDO::getInviteeUserId, inviteeUserId)
                .eq(InviteUserDO::getInviterUserId, inviterUserId)
                .in(InviteUserDO::getStatus, statusList));
    }
}
