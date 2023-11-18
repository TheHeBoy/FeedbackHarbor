package cn.iocoder.yudao.module.system.api.user.dto;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户 Response DTO
 */
@Data
public class UserRespDTO {

    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 帐号状态
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户类型
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;
}
