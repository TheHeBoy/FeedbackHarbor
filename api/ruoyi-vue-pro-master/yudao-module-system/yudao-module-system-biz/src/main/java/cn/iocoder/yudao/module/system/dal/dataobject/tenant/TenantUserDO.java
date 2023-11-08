package cn.iocoder.yudao.module.system.dal.dataobject.tenant;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 租户和用户关联 DO
 */
@TableName(value = "system_tenant_user", autoResultMap = true)
@KeySequence("system_tenant_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
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
