package cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理后台 - 用户反馈创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackCreateReqVO extends FeedbackBaseVO {

    @Schema(description = "用户id", example = "16979")
    private Long uid;
}
