package cn.iocoder.yudao.module.harbor.controller.admin.appuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Schema(description = "管理后台 - App用户更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppUserUpdateReqVO extends AppUserBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "16238")
    @NotNull(message = "主键不能为空")
    private Long id;

}
