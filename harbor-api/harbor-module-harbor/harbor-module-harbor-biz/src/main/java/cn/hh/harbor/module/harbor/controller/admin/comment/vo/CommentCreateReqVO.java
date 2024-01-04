package cn.hh.harbor.module.harbor.controller.admin.comment.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理后台 - 评论创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommentCreateReqVO extends CommentBaseVO {

}
