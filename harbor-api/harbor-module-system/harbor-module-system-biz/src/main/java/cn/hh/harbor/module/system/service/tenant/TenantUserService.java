package cn.hh.harbor.module.system.service.tenant;

import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantUserDO;

public interface TenantUserService {

    /**
     * 插入租户和用户的关系
     *
     * @param userId 用户id
     * @param tenantId 租户id
     */
    void insert(Long tenantId, Long userId);

    /**
     * 得到租户和用户的关系
     *
     * @param userId   用户id
     * @param tenantId 租户id
     * @return 租户和用户关系对象
     */
    TenantUserDO get(Long tenantId, Long userId);
}
