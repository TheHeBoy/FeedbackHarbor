package cn.hh.harbor.module.system.api.tenant;

import cn.hh.harbor.module.system.api.tenant.dto.TenantRespDTO;

import java.util.List;

/**
 * 多租户的 API 接口
 *
 *
 */
public interface TenantApi {

    /**
     * 获得所有租户
     *
     * @return 租户编号数组
     */
    List<Long> getTenantIdList();

    /**
     * 校验租户是否合法
     *
     * @param id 租户编号
     */
    void validateTenant(Long id);

    /**
     * 校验租户路由uri是否存在
     * @param routerUri 路由uri
     * @return {@link TenantRespDTO} 存在时返回对象 不存在时返回 null
     */
    TenantRespDTO checkTenantRouterUri(String routerUri);

    /**
     * 校验租户和用户是否存在管理，存在就表示用户属于当前租户的管理员
     * @param tenantId 租户id
     * @param userId 用户id
     * @return 存在返回 true
     */
    boolean checkTenantUser(Long tenantId,Long userId);
}
