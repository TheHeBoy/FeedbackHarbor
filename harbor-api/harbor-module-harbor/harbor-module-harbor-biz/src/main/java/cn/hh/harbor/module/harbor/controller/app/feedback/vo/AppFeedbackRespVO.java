package cn.hh.harbor.module.harbor.controller.app.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "App - 用户反馈 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppFeedbackRespVO extends AppFeedbackBaseVO {

}
