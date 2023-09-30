package cn.iocoder.yudao.module.harbor.controller.app.feedback.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.validation.InEnum;
import cn.iocoder.yudao.module.harbor.enums.feedback.FeedbackOrderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "App - 用户反馈分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppFeedbackPageReqVO extends PageParam {

    @Schema(description = "反馈类型")
    private Integer feedbackType;

    @Schema(description = "排序")
    @InEnum(FeedbackOrderEnum.class)
    @NotNull(message = "排序不能为空")
    private Integer order;
}
