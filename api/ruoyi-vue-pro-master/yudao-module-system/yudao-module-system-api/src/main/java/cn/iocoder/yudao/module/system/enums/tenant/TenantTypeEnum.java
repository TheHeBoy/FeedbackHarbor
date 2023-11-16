package cn.iocoder.yudao.module.system.enums.tenant;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 租户类型
 *
 * @author hehong
 * @date 2023-11-11
 */
public class TenantTypeEnum {

    // 内置租户ID 管理整个系统
    private static final Long tenantSystemId = 1L;

    /**
     * 是否是内置租户
     */
    public static boolean isSystemTenant(Long tenantId) {
        return ObjectUtil.equals(tenantId, tenantSystemId);
    }
}