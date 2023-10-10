package cn.iocoder.yudao.module.harbor.controller.app.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;

@Schema(description = "App - 评论创建 Request VO")
@Data
@ToString(callSuper = true)
public class AppCommentCreateReqVO {

    @Schema(description = "父id")
    private Long parentId;

    @Schema(description = "反馈id")
    @NotNull
    private Long feedbackId;

    @Schema(description = "内容")
    @NotBlank
    @Size(min = 1,max = 3000)//因为有换行符等，就设置为 &nbsp;的长度6 * 500
    private String content;

    @Schema(description = "反馈图片集")
    private String imgs;
}
