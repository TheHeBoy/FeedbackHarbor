package cn.iocoder.yudao.framework.common.enums;

import cn.hutool.core.util.ObjectUtil;

/**
 * 系统内置数据id枚举
 * 用于判断用户禁止删除系统内置数据等
 *
 * @author hehong
 * @date 2023-11-11
 */
public class SystemIdEnum {

    // 系统内置数据id, Redis Id在此最小值上增加
    public static final Long SYSTEM_ID = 100000L;

    /**
     * 是否是内置系统数据
     */
    public static boolean isSystemData(Long id) {
        return id < SYSTEM_ID;
    }
}