package cn.hh.harbor.module.system.api.token.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Token 访问令牌的信息 Response DTO
 */
@Data
@Accessors(chain = true)
public class TokenRespDTO implements Serializable {

    /**
     * 访问令牌
     */
    private String accessToken;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;

}
