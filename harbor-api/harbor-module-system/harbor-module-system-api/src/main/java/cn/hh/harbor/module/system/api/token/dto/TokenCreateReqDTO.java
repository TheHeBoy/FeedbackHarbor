package cn.hh.harbor.module.system.api.token.dto;

import cn.hh.harbor.framework.common.enums.UserTypeEnum;
import cn.hh.harbor.framework.common.validation.InEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Token 访问令牌创建 Request DTO
 *
 *
 */
@Data
public class TokenCreateReqDTO implements Serializable {

    /**
     * 用户编号
     */
    @NotNull(message = "用户编号不能为空")
    private Long userId;
    /**
     * 授权租户编号
     */
    @NotNull(message = "租户不能为空")
    private Long tenantId;
}
