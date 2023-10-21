package cn.iocoder.yudao.module.harbor.controller.app.comment.vo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "App - 评论 Response VO")
@Data
@ToString(callSuper = true)
public class AppCommentPageRespVO extends AppCommentBaseVO{

    @Schema(description = "回复分页集合")
    private PageResult<ReplyVO> replyPage;
}
