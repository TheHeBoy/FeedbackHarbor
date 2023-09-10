package cn.iocoder.yudao.module.harbor.controller.admin.appuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - App用户 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppUserRespVO extends AppUserBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "16238")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
