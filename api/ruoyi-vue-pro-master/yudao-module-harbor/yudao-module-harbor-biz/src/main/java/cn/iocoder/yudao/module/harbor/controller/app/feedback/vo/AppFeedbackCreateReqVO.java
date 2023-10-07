package cn.iocoder.yudao.module.harbor.controller.app.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;

@Schema(description = "App - 用户反馈创建 Request VO")
@Data
@ToString(callSuper = true)
public class AppFeedbackCreateReqVO {

    @Schema(description = "内容")
    @NotEmpty
    @Size(min = 5,max = 500)
    private String content;

    @Schema(description = "反馈标签id")
    @NotNull
    private Long feedbackTagId;

    @Schema(description = "反馈图片集")
    private String imgs;
}
