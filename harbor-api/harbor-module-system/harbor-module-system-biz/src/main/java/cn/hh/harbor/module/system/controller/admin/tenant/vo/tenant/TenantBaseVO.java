package cn.hh.harbor.module.system.controller.admin.tenant.vo.tenant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * 租户 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TenantBaseVO {

    @Schema(description = "租户名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
    @NotNull(message = "租户名不能为空")
    private String name;

    @Schema(description = "租户状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "租户状态")
    private Integer status;

    @Schema(description = "租户套餐编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "租户套餐编号不能为空")
    private Long packageId;

    @Schema(description = "过期时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "过期时间不能为空")
    private LocalDateTime expireTime;
}
