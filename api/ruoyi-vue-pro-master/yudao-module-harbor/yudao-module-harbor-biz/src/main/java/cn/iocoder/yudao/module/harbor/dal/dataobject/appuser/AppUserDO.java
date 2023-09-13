package cn.iocoder.yudao.module.harbor.dal.dataobject.appuser;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import cn.iocoder.yudao.module.harbor.enums.appuser.AppUserTypeEnum;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * App用户 DO
 *
 *  hehong
 */
@TableName("harbor_app_user")
@KeySequence("harbor_app_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDO extends TenantBaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 用户类型
     *
     * 枚举 {@link AppUserTypeEnum}
     */
    private Integer userType;
    /**
     * 用户账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 登录态用户id
     */
    private String userOpenId;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

}
