package cn.hh.harbor.module.system.controller.admin.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Schema(description = "管理后台 - 邮箱注册 Request VO")
@Data
public class AuthMailRegisterReqVO {
    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "harboryuanma")
    @NotEmpty(message = "账号不能为空")
    @Length(min = 4, max = 16, message = "账号长度为 4-16 位")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "账号格式为数字以及字母")
    private String username;

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
