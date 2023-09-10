package cn.iocoder.yudao.module.harbor.controller.app.comment.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "App - 用户评论分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppCommentPageReqVO extends PageParam {

    @NotNull(message = "反馈id不能为空")
    @Schema(description = "反馈id")
    private Long feedbackId;
}
