package cn.hh.harbor.module.system.dal.dataobject.logger;

import cn.hh.harbor.framework.common.enums.UserTypeEnum;
import cn.hh.harbor.framework.mybatis.core.dataobject.BaseDO;
import cn.hh.harbor.module.system.enums.logger.LoginLogTypeEnum;
import cn.hh.harbor.module.system.enums.logger.LoginResultEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 登录日志表
 *
 * 注意，包括登录和登出两种行为
 *
 *
 */
@TableName("system_login_log")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LoginLogDO extends BaseDO {

    /**
     * 日志主键
     */
    private Long id;
    /**
     * 日志类型
     *
     * 枚举 {@link LoginLogTypeEnum}
     */
    private Integer logType;
    /**
     * 链路追踪编号
     */
    private String traceId;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户账号
     *
     * 冗余，因为账号可以变更
     */
    private String username;
    /**
     * 登录结果
     *
     * 枚举 {@link LoginResultEnum}
     */
    private Integer result;
    /**
     * 用户 IP
     */
    private String userIp;
    /**
     * 浏览器 UA
     */
    private String userAgent;

}
