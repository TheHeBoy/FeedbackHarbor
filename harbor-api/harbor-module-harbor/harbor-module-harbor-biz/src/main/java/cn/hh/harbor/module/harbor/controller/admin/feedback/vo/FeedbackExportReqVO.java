package cn.hh.harbor.module.harbor.controller.admin.feedback.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.hh.harbor.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 用户反馈 Excel 导出 Request VO，参数和 FeedbackPageReqVO 是一致的")
@Data
public class FeedbackExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "用户id", example = "16979")
    private Long uid;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "点赞数")
    private Long likes;

    @Schema(description = "反馈标签id")
    private Long feedbackTagId;

}
