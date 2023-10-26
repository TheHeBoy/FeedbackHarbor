package cn.iocoder.yudao.module.harbor.dal.dataobject.appuser;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Admin和App用户关联表
 *
 *
 */
@TableName(value = "harbor_admin_app_user", autoResultMap = true) // 由于 SQL Server 的 system_user 是关键字，所以使用 system_users
@KeySequence("system_admin_app_user") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminAppUserDO extends TenantBaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * admin user id
     */
    private Long adminId;

    /**
     * app user id
     */
    private Long appId;
}
