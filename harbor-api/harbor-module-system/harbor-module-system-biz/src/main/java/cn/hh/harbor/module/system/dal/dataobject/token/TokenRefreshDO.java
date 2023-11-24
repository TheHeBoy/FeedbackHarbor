package cn.hh.harbor.module.system.dal.dataobject.token;

import cn.hh.harbor.framework.common.enums.UserTypeEnum;
import cn.hh.harbor.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Token 刷新令牌
 */
@TableName(value = "system_token_refresh", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TokenRefreshDO extends BaseDO {

    /**
     * 编号，数据库字典
     */
    private Long id;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;

}
