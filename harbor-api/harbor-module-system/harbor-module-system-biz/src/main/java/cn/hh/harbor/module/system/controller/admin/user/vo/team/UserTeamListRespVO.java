package cn.hh.harbor.module.system.controller.admin.user.vo.team;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Schema(description = "管理后台 - 管理团队 Response VO")
@EqualsAndHashCode(callSuper = true)
public class UserTeamListRespVO extends UserTeamBaseVO {

    @Schema(description = "角色id集合", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<Long> roleIds;
}
