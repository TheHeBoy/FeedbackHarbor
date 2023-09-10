package cn.iocoder.yudao.module.harbor.controller.app.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "App - 评论创建 Request VO")
@Data
@ToString(callSuper = true)
public class AppCommentCreateReqVO {

    @Schema(description = "父id")
    private Long parentId;

    @Schema(description = "反馈id")
    private Long feedbackId;

    @Schema(description = "内容")
    private String content;
}
