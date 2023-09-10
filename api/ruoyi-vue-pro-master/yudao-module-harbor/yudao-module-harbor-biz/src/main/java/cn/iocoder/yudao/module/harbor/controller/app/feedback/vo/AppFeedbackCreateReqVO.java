package cn.iocoder.yudao.module.harbor.controller.app.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "App - 用户反馈创建 Request VO")
@Data
@ToString(callSuper = true)
public class AppFeedbackCreateReqVO {

    @Schema(description = "内容")
    private String content;

    @Schema(description = "反馈类型", example = "1")
    private Integer feedbackType;
}
