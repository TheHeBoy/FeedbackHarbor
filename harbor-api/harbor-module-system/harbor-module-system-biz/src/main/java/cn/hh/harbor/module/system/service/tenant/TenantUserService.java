package cn.hh.harbor.module.system.service.tenant;

public interface TenantUserService {

    /**
     * 插入租户和用户的关系
     *
     * @param userId 用户id
     * @param tenantId 租户id
     */
    void insert(Long tenantId, Long userId);
}
