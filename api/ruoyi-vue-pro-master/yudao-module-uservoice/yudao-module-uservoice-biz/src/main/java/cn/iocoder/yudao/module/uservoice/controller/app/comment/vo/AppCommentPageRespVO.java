package cn.iocoder.yudao.module.uservoice.controller.app.comment.vo;

import cn.iocoder.yudao.module.uservoice.dal.dataobject.comment.CommentDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Schema(description = "App - 评论 Response VO")
@Data
@ToString(callSuper = true)
public class AppCommentPageRespVO extends AppCommentBaseVO{

    @Schema(description = "回复集合")
    private List<ReplyVO> replies;
}
