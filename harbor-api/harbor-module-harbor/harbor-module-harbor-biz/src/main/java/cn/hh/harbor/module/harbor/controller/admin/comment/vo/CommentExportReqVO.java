package cn.hh.harbor.module.harbor.controller.admin.comment.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.hh.harbor.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 评论 Excel 导出 Request VO，参数和 CommentPageReqVO 是一致的")
@Data
public class CommentExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "父节点", example = "524")
    private Long parentId;

    @Schema(description = "用户id", example = "21744")
    private Long uid;

    @Schema(description = "反馈id", example = "21567")
    private Long feedbackId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "点赞数")
    private Long likes;

    @Schema(description = "用户类型;", example = "2")
    private Integer userType;

    @Schema(description = "头像;")
    private String avatar;

    @Schema(description = "用户昵称;", example = "赵六")
    private String nickname;

}
