package cn.iocoder.yudao.module.system.api.tenant.dto;


import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TenantRespDTO {
    /**
     * 租户编号，UUID
     */
    private Long id;
    /**
     * 租户名，唯一
     */
    private String name;
    /**
     * 产品Logo
     */
    private String logo;
    /**
     * 租户路由 Uri
     */
    private String routerUri;
}
