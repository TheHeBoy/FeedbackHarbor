package cn.iocoder.yudao.module.harbor.controller.admin.comment.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 评论分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommentPageReqVO extends PageParam {

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
