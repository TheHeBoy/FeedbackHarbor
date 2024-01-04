package cn.hh.harbor.module.harbor.controller.app.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;
import java.util.List;

@Schema(description = "App - 用户反馈创建 Request VO")
@Data
@ToString(callSuper = true)
public class AppFeedbackCreateReqVO {

    @Schema(description = "内容")
    @NotBlank
    @Size(min = 5,max = 3000) //因为有换行符等，就设置为 &nbsp;的长度6 * 500
    private String content;

    @Schema(description = "反馈标签id")
    @NotNull
    private Long feedbackTagId;

    @Schema(description = "反馈图片集")
    private List<String> imgs;
}
