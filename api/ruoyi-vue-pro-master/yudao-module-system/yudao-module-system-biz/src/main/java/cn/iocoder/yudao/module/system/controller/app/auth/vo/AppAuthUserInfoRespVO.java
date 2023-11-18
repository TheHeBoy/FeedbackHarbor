package cn.iocoder.yudao.module.system.controller.app.auth.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 应用身份验证登录resp
 *
 * @author hehong
 * @date 2023-08-16
 */
@Data
public class AppAuthUserInfoRespVO {

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道源码")
    private String nickname;

    @Schema(description = "用户头像", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn/xx.jpg")
    private String avatar;

    @Schema(description = "用户类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer userType;

}
