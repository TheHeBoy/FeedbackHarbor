package cn.iocoder.yudao.module.system.enums.tenant;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.util.object.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TenantPackageTypeEnum {

    /**
     * 默认套餐 新建租户的默认套餐不能删除
     */
    DEF(1),
    /**
     * 自定义套餐
     */
    CUSTOM(2);

    private final Integer type;


    public static boolean isDefault(int type) {
        return ObjectUtil.equal(DEF.getType(), type);
    }
}
