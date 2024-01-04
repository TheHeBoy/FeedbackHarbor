package cn.hh.harbor.module.harbor.controller.admin.home.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.hh.harbor.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Data
public class HomeStatisticsRespVO {
    
    @Schema(description = "反馈统计", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long feedbackTotal;
}
