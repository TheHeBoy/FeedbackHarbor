package cn.hh.harbor.module.system.dal.dataobject.token;

import cn.hh.harbor.framework.common.enums.UserTypeEnum;
import cn.hh.harbor.framework.mybatis.core.dataobject.BaseDO;
import cn.hh.harbor.framework.mybatis.core.type.StringListTypeHandler;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Token 访问令牌 DO
 */
@TableName(value = "system_token_access", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class TokenAccessDO extends BaseDO {

    /**
     * 编号，数据库递增
     */
    @TableId
    private Long id;
    /**
     * 访问令牌
     */
    private String accessToken;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;
    /**
     * 授权的社区租户id
     */
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<Long> tenantIds;
}
