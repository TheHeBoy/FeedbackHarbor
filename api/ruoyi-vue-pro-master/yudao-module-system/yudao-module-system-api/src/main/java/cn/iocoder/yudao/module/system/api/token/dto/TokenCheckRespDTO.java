package cn.iocoder.yudao.module.system.api.token.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Token 访问令牌的校验 Response DTO
 *
 * 
 */
@Data
public class TokenCheckRespDTO implements Serializable {

    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 租户编号
     */
    private List<Long> tenantIds;
}
