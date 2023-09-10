package cn.iocoder.yudao.module.harbor.controller.admin.appuser.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - App用户 Excel 导出 Request VO，参数和 AppUserPageReqVO 是一致的")
@Data
public class AppUserExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "用户类型", example = "1")
    private Byte userType;

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
    private Byte status;

}
