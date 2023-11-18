package cn.iocoder.yudao.framework.common.enums;

import cn.hutool.core.util.ObjectUtil;

/**
 * 系统内置数据id枚举
 * 用于避免用户删除系统内置数据
 *
 * @author hehong
 * @date 2023-11-11
 */
public class SystemIdEnum {

    // 系统内置数据id
    public static final Long SYSTEM_ID = 1L;

    /**
     * 是否是内置租户
     */
    public static boolean isSystemData(Long id) {
        return ObjectUtil.equals(SYSTEM_ID, id);
    }
}