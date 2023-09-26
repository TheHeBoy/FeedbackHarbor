package cn.iocoder.yudao.module.harbor.controller.app.appuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "App - 用户创建 Request VO")
@Data
@ToString(callSuper = true)
public class AppUserCreateReqVO {


    @Schema(description = "用户类型")
    private Integer userType;

    @Schema(description = "外部用户id")
    private String userOpenId;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "用户昵称")
    private String nickname;
}
