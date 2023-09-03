package cn.iocoder.yudao.module.uservoice.controller.app.comment.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "App - 评论创建 Request VO")
@Data
@ToString(callSuper = true)
public class AppCommentCreateReqVO {

    @Schema(description = "用户id")
    private Long uid;

    @Schema(description = "父id")
    private Long parentId;

    @Schema(description = "反馈id")
    private Long feedbackId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "用户类型")
    private Integer userType;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "用户昵称")
    private String nickname;
}
