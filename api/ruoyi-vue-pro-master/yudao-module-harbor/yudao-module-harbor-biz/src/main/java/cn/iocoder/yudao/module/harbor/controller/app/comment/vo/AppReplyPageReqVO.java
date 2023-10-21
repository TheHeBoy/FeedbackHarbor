package cn.iocoder.yudao.module.harbor.controller.app.comment.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "App - 用户回复分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppReplyPageReqVO extends PageParam {

    @NotNull(message = "父级评论id不能为空")
    @Schema(description = "父级评论id")
    private Long commentId;
}
