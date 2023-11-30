package cn.hh.harbor.module.system.controller.admin.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 重置密码 Request VO")
@Data
public class AuthResetPasswdReqVO {

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "buzhidao")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;

    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "邮箱不能为空")
    @Email
    private String mail;

    @Schema(description = "邮箱验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "验证码不能为空")
    private String captcha;
}
