package cn.hh.harbor.module.system.api.token.dto;

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
     * 租户编号
     */
    private List<Long> tenantIds;
}
