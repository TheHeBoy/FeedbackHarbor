package cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 用户反馈 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FeedbackBaseVO {

    @Schema(description = "用户id", example = "16979")
    private Long uid;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "点赞数")
    private Long likes;

    @Schema(description = "反馈类型", example = "1")
    private Integer feedbackType;

}
