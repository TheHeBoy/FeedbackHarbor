package cn.iocoder.yudao.module.system.enums.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据范围枚举类
 * <p>
 * 用于实现数据级别的权限
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum {

    ALL(1), // 全部数据权限
    SELF(2); // 仅本人数据权限

    /**
     * 范围
     */
    private final Integer scope;

}
