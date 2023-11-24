package cn.hh.harbor.module.system.service.invite;

import cn.hh.harbor.module.system.controller.admin.invite.vo.InviteUserReqVO;
import cn.hh.harbor.module.system.dal.dataobject.invite.InviteDO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InviteService {

    /**
     * 用户邀请
     *
     * @param reqVO 邀请参数
     */
    void inviteUser(InviteUserReqVO reqVO, Long tenantId);


    /**
     * 通过用户id,查看此用户的收到的待回复用户邀请
     *
     * @param inviteeUserId 用户id
     * @return 邀请集合
     */
    List<InviteDO> selectListByInviteeUserId(Long inviteeUserId);

    /**
     * 接收用户邀请
     *
     * @param id 主键
     */
    void acceptUserInvite(Long id);

    /**
     * 拒绝用户邀请
     *
     * @param id 主键
     */
    void refuseUserInvite(Long id);
}
