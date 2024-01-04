package cn.hh.harbor.module.system.controller.admin.user.vo.team;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "管理后台 - 管理团队 Base VO")
public class UserTeamBaseVO {
    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "用户账号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;
}
