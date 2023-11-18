package cn.iocoder.yudao.module.harbor.controller.app.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.time.LocalDateTime;

/**
 * 评论 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AppCommentBaseVO {

    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "父节点")
    private Long parentId;

    @Schema(description = "用户id")
    private Long uid;

    @Schema(description = "反馈id")
    private Long feedbackId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "点赞数")
    private Long likes;

    @Schema(description = "用户类型")
    private Integer userType;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "评论图片集")
    private List<String> imgs;
}
