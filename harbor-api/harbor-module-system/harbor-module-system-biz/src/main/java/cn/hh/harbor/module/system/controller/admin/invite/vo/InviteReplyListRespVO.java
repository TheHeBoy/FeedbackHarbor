package cn.hh.harbor.module.system.controller.admin.invite.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户被邀列表 VO
 *
 * @author hehong
 * @date 2023-11-25
 */
@Data
public class InviteReplyListRespVO {

    @Schema(description = "邀请id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;

    @Schema(description = "用户头像", requiredMode = Schema.RequiredMode.REQUIRED)
    private String avatar;

    @Schema(description = "租户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tenantName;

    @Schema(description = "租户logo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tenantLogo;
}
