package cn.hh.harbor.module.system.service.invite;

public interface InviteLinkService {
    /**
     * 创建邀请链接
     *
     * @param tenantId 租户id
     * @return {@link String} 邀请链接标识code
     */
    String create(Long tenantId);

    /**
     * 得到链接标识
     * 不存在返回 null
     *
     * @param tenantId 租户id
     * @return {@link String} 邀请链接标识code
     */
    String get(Long tenantId);

    /**
     * 删除邀请链接
     *
     * @param code 链接标识
     */
    void delete(String code);

    /**
     * 通过链接标识加入团队
     *
     * @param code   链接标识
     * @param userId 加入团队的用户 id
     */
    void joinByCode(String code, Long userId);
}
