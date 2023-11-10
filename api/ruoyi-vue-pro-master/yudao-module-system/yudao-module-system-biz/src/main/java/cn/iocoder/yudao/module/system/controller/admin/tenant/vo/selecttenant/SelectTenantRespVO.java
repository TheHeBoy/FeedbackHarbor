package cn.iocoder.yudao.module.system.controller.admin.tenant.vo.selecttenant;

import cn.iocoder.yudao.module.system.controller.admin.tenant.vo.tenant.TenantBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 租户 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SelectTenantRespVO extends SelectTenantBaseVO {

    @Schema(description = "租户编号")
    private Long id;

    @Schema(description = "租户类型")
    private Long type;
}
