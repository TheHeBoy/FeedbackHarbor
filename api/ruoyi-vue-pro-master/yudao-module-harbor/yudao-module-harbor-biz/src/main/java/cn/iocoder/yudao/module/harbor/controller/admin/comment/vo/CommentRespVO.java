package cn.iocoder.yudao.module.harbor.controller.admin.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 评论 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommentRespVO extends CommentBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "22079")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
