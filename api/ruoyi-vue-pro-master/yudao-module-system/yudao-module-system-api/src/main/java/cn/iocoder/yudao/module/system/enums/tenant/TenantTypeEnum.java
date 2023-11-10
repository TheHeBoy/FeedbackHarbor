package cn.iocoder.yudao.module.system.enums.tenant;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 租户类型
 *
 * @author hehong
 * @date 2023-11-11
 */
@Getter
@AllArgsConstructor
public enum TenantTypeEnum {
    /**
     * 内置租户 反馈港租户-管理整个系统
     */
    SYSTEM(1),
    /**
     * 自定义租户 反馈社区租户-管理反馈社区
     */
    CUSTOM(2);

    private final Integer type;


    /**
     * 是否是内置租户
     */
    public static boolean isSystemTenant(Integer type) {
        return ObjectUtil.equals(type, SYSTEM.type);
    }
}