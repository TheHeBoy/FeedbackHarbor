package cn.hh.harbor.module.system.controller.admin.invite.vo.link;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class InviteSendMailReqVO {

    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "邮箱不能为空")
    List<@Email String> mails;

    @Schema(description = "租户id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "租户id不能为空")
    Long tenantId;

    @Schema(description = "前端登录页面url", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "前端登录页面url不能为空")
    String loginUrl;
}
