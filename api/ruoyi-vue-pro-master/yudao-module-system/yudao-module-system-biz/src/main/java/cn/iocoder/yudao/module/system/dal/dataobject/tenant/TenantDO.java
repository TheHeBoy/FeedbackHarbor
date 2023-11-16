package cn.iocoder.yudao.module.system.dal.dataobject.tenant;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.enums.tenant.TenantTypeEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 租户 DO
 */
@TableName(value = "system_tenant", autoResultMap = true)
@KeySequence("system_tenant_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantDO extends BaseDO {
    /**
     * 租户编号，UUID
     */
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 租户名，唯一
     */
    private String name;
    /**
     * 租户状态
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 租户套餐编号
     * 关联 {@link TenantPackageDO#getId()}
     */
    private Long packageId;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    /**
     * 产品Logo
     */
    private String logo;
    /*
     * 路由uri
     */
    private String routerUri;
}
