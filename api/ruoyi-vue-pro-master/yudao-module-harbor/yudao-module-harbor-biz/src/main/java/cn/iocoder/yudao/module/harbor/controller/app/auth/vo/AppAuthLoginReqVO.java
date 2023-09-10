package cn.iocoder.yudao.module.harbor.controller.app.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Schema(description = "用户- 手机 + 密码登录")
@Data
public class AppAuthLoginReqVO {

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED, example = "619215399")
    @NotEmpty(message = "用户名不能为空")
    @Length(min = 4, max = 16, message = "用户名长度为 4-16 位")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;
}
