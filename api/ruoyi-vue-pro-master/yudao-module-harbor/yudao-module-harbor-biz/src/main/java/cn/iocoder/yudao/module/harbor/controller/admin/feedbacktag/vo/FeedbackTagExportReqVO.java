package cn.iocoder.yudao.module.harbor.controller.admin.feedbacktag.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 反馈标签 Excel 导出 Request VO，参数和 FeedbackTagPageReqVO 是一致的")
@Data
public class FeedbackTagExportReqVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "标签名中文")
    private String nameCh;

    @Schema(description = "标签名英语")
    private String nameEn;

    @Schema(description = "标签顺序")
    private Long sort;

}
