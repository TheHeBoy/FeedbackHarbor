package cn.iocoder.yudao.module.system.controller.admin.tenant.vo.selecttenant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 租户选择界面 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SelectTenantBaseVO {

    @Schema(description = "租户名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
    @NotEmpty(message = "租户名不能为空")
    @Length(max = 30, message = "租户名不能超过30")
    private String name;

    @Schema(description = "社区租户Logo", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "社区Logo不能为空")
    private String logo;

    @Schema(description = "路由Uri", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "路由Uri不能为空")
    @Length(max = 30, message = "路由Uri超过30")
    private String routerUri;
}
