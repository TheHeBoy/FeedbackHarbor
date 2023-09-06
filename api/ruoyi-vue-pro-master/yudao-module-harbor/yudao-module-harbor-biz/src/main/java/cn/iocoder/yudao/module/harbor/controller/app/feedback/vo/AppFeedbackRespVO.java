package cn.iocoder.yudao.module.harbor.controller.app.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "App - 用户反馈 Response VO")
@Data
public class AppFeedbackRespVO {

    @Schema(description = "主键id")
    private String id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "点赞数")
    private Long likes;

    @Schema(description = "反馈类型", example = "1")
    private Integer feedbackType;

    @Schema(description = "用户id", example = "16979")
    private Long uid;

    @Schema(description = "用户昵称", example = "1")
    private String nickname;

    @Schema(description = "头像", example = "1")
    private String avatar;
}
