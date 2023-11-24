package cn.hh.harbor.module.system.dal.dataobject.tenant;

import cn.hh.harbor.framework.common.enums.CommonStatusEnum;
import cn.hh.harbor.framework.mybatis.core.dataobject.BaseDO;
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
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantDO extends BaseDO {
    /**
     * 租户编号
     */
    @TableId(value = "ID")
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
