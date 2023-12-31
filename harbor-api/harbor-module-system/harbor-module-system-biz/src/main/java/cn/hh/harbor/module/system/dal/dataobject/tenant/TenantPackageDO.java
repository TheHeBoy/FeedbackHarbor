package cn.hh.harbor.module.system.dal.dataobject.tenant;

import cn.hh.harbor.framework.mybatis.core.dataobject.BaseDO;
import cn.hh.harbor.framework.mybatis.core.type.JsonLongSetTypeHandler;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Set;

/**
 * 租户套餐 DO
 */
@TableName(value = "system_tenant_package", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantPackageDO extends BaseDO {

    /**
     * 套餐编号，自增
     */
    private Long id;
    /**
     * 套餐名，唯一
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
    /**
     * 关联的菜单编号
     */
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> menuIds;
    /**
     * 套餐天数，-1表示永久
     */
    private Integer days;
}
