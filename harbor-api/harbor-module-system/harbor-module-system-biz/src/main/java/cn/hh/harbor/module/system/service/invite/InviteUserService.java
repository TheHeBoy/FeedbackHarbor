package cn.hh.harbor.module.system.service.invite;

import cn.hh.harbor.module.system.controller.admin.invite.vo.user.InviteUserReqVO;
import cn.hh.harbor.module.system.dal.dataobject.invite.InviteUserDO;

import java.util.List;


public interface InviteUserService {

    /*--------- 用户邀请 ---------*/

    /**
     * 用户邀请
     *
     * @param reqVO 邀请参数
     */
    void inviteUser(InviteUserReqVO reqVO);


    /**
     * 通过用户id,查看此用户的收到的待回复用户邀请
     *
     * @param inviteeUserId 用户id
     * @return 邀请集合
     */
    List<InviteUserDO> selectListByInviteeUserId(Long inviteeUserId);

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


    /**
     * 通过邀请人和被邀请人得到邀请信息
     * 只包括待回复和拒绝的
     *
     * @param inviteeUserId 用户id
     * @return 邀请集合
     */
    InviteUserDO selectUserInviteFilter(Long inviteeUserId, Long inviterUserId);

    /*--------- 邮件邀请 ---------*/
}
