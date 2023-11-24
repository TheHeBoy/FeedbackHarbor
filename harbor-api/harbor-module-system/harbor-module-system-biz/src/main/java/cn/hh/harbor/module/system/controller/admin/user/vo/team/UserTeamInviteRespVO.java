package cn.hh.harbor.module.system.controller.admin.user.vo.team;

import cn.hh.harbor.framework.common.validation.InEnum;
import cn.hh.harbor.module.system.enums.invite.InviteStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(description = "管理后台 - 管理团队 Response VO")
@EqualsAndHashCode(callSuper = true)
public class UserTeamInviteRespVO extends UserTeamBaseVO {

    @Schema(description = "邀请状态")
    @InEnum(InviteStatusEnum.class)
    private Integer inviteStatus;
}
