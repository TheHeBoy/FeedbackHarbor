package cn.hh.harbor.module.system.controller.admin.invite.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户邀请
 * @author hehong
 * @date 2023-11-24
 */
@Data
public class InviteUserReqVO {

    @Schema(description = "邀请人用户id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "邀请人用户Id不能为空")
    private Long inviterUserId;

    @Schema(description = "受邀人用户id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "受邀人用户Id不能为空")
    private List<Long> inviteeUserIds;

    @Schema(description = "租户id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "租户id不能为空")
    private Long tenantId;
}
