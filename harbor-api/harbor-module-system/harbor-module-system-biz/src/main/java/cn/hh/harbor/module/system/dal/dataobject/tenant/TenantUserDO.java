package cn.hh.harbor.module.system.dal.dataobject.tenant;

import cn.hh.harbor.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 租户和用户关联 DO
 */
@TableName(value = "system_tenant_user", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantUserDO extends BaseDO {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 租户ID
     */
    private Long tenantId;
}
