package cn.iocoder.yudao.module.system.controller.admin.tenant.vo.selecttenant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 租户更新 Request VO")
@Data
@ToString(callSuper = true)
public class SelectTenantUpdateReqVO {
    @Schema(description = "租户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "租户编号不能为空")
    private Long id;

    @Schema(description = "租户名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
    @NotNull(message = "租户名不能为空")
    private String name;

    @Schema(description = "社区租户Logo", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "社区Logo不能为空")
    private String logo;
}
