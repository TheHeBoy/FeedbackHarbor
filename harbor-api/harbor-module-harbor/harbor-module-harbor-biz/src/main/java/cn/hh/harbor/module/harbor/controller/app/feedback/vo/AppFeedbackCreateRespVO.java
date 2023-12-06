package cn.hh.harbor.module.harbor.controller.app.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "App - 创建用户反馈 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppFeedbackCreateRespVO extends AppFeedbackBaseVO {

    @Schema(description = "敏感词-如果不为空表示有敏感词")
    private List<String> sensitive;
}
