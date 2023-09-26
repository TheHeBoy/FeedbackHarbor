package cn.iocoder.yudao.module.harbor.controller.app.auth.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息
 *
 * @author hehong
 * @date 2023-08-19
 */
@Data
public class AppAuthLoginRespVO {
    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    @Schema(description = "访问令牌", requiredMode = Schema.RequiredMode.REQUIRED)
    private String accessToken;

    @Schema(description = "过期时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime expiresTime;
}
