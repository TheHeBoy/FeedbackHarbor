package cn.hh.harbor.module.system.service.invite;

import cn.hh.harbor.module.system.controller.admin.invite.vo.link.InviteSendMailReqVO;

public interface InviteLinkService {
    /**
     * 创建邀请链接
     *
     * @param tenantId 租户id
     * @return {@link String} 邀请链接标识code
     */
    String createLink(Long tenantId);

    /**
     * 得到链接标识
     * 不存在返回 null
     *
     * @param tenantId 租户id
     * @return {@link String} 邀请链接标识code
     */
    String getLink(Long tenantId);

    /**
     * 删除邀请链接
     *
     * @param code 链接标识
     */
    void deleteByCode(String code);

    /**
     * 通过链接标识加入团队
     *
     * @param code   链接标识
     * @param userId 加入团队的用户 id
     */
    void joinByCode(String code, Long userId);

    /**
     * 发送邀请邮件
     *
     * @param reqVO 邀请信息
     * @param userId 邀请人用户 id
     */
    void sendInviteMail(InviteSendMailReqVO reqVO, Long userId);
}
