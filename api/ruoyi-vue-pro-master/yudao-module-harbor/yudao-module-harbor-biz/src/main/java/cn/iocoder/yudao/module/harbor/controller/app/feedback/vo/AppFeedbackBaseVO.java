package cn.iocoder.yudao.module.harbor.controller.app.feedback.vo;

import cn.iocoder.yudao.module.harbor.controller.app.feedbacktag.vo.AppFeedbackTagRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 反馈基础 Base VO
 *
 * @author hehong
 * @date 2023-10-26
 */
@Data
public class AppFeedbackBaseVO {
    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "点赞数")
    private Long likes;

    @Schema(description = "反馈标签id")
    private AppFeedbackTagRespVO feedbackTag;

    @Schema(description = "用户id", example = "16979")
    private Long uid;

    @Schema(description = "用户昵称", example = "1")
    private String nickname;

    @Schema(description = "头像", example = "1")
    private String avatar;

    @Schema(description = "评论数")
    private Long commentNum;

    @Schema(description = "评论图片集")
    private String imgs;

    @Schema(description = "用户类型")
    private Integer userType;
}
