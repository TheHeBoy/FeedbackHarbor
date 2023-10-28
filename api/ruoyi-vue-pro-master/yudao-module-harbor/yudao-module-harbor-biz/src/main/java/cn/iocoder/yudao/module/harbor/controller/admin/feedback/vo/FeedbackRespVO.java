package cn.iocoder.yudao.module.harbor.controller.admin.feedback.vo;

import cn.iocoder.yudao.module.harbor.controller.app.feedback.vo.AppFeedbackRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 用户反馈 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackRespVO extends AppFeedbackRespVO {

    @Schema(description = "反馈回复状态")
    private Integer replyState;
}
