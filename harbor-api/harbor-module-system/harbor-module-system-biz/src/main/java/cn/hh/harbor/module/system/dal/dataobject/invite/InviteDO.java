package cn.hh.harbor.module.system.dal.dataobject.invite;

import cn.hh.harbor.framework.mybatis.core.dataobject.BaseDO;
import cn.hh.harbor.module.system.enums.invite.InviteStatusEnum;
import cn.hh.harbor.module.system.enums.invite.InviteTypeEnum;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 管理团队邀请
 *
 * @author hehong
 * @date 2023-11-24
 */
@TableName(value = "system_invite", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InviteDO extends BaseDO {
    /**
     * 主键
     */
    @TableId(value = "ID")
    private Long id;
    /**
     * 邀请人的用户id
     */
    private Long inviterUserId;
    /**
     * 邀请标识uuid
     */
    private String token;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    /**
     * 受邀人用户id
     */
    private Long inviteeUserId;
    /**
     * 受邀人邮件
     */
    private String inviteeEmail;
    /**
     * 邮件备注
     */
    private String emailRemark;
    /**
     * 受邀状态
     * 枚举 {@link InviteStatusEnum}
     */
    private Integer status;
    /**
     * 邀请类型
     * 枚举 {@link InviteTypeEnum}
     */
    private Integer type;
    /**
     * 多租户编号
     */
    private Long tenantId;
}
