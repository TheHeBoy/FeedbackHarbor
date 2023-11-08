package cn.iocoder.yudao.module.system.controller.admin.tenant.vo.selecttenant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 租户创建 Request VO")
@Data
@ToString(callSuper = true)
public class SelectTenantCreateReqVO {

    @Schema(description = "租户名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
    @NotNull(message = "租户名不能为空")
    private String name;

    @Schema(description = "社区租户Logo", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "社区Logo不能为空")
    private String logo;
}
