package cn.iocoder.yudao.module.system.enums.permission;

import cn.iocoder.yudao.framework.common.util.object.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色标识枚举
 */
@Getter
@AllArgsConstructor
public enum RoleCodeEnum {

    SUPER_ADMIN("super_admin", "超级管理员"),
    ADMIN("super_admin", "系统管理员"),
    SUPER_TENANT_ADMIN("super_tenant_admin", "超级租户管理员"),
    TENANT_ADMIN("tenant_admin", "租户管理员"),
    ;

    /**
     * 角色编码
     */
    private final String code;
    /**
     * 名字
     */
    private final String name;

    public static boolean isSuperAdmin(String code) {
        return ObjectUtils.equalsAny(code, SUPER_ADMIN.getCode());
    }
    public static boolean isSuperTenantAdmin(String code) {
        return ObjectUtils.equalsAny(code, SUPER_TENANT_ADMIN.getCode());
    }

    public static boolean isTenantAdmin(String code) {
        return ObjectUtils.equalsAny(code, TENANT_ADMIN.getCode());
    }
}
