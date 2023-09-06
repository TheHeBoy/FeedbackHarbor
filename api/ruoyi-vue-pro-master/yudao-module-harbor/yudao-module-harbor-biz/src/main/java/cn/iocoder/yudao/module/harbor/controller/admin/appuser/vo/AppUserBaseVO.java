package cn.iocoder.yudao.module.harbor.controller.admin.appuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

/**
 * App用户 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AppUserBaseVO {

    @Schema(description = "用户类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    @Schema(description = "用户账号", example = "芋艿")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "登录态用户id", example = "22660")
    private String userOpenId;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "用户昵称", example = "李四")
    private String nickname;

    @Schema(description = "用户状态", example = "1")
    private Integer status;

}
