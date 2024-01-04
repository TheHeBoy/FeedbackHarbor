package cn.hh.harbor.module.harbor.controller.app.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Schema(description = "App - 创建评论 Response VO")
@Data
@ToString(callSuper = true)
public class AppCommentCreateRespVO extends AppCommentBaseVO {
    @Schema(description = "敏感词-如果不为空表示有敏感词")
    private List<String> sensitive;
}
