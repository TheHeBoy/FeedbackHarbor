package cn.hh.harbor.module.system.dal.dataobject.invite;

import cn.hh.harbor.framework.mybatis.core.dataobject.BaseDO;
import cn.hh.harbor.module.system.enums.invite.user.InviteStatusEnum;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 管理团队邀请
 *
 * @author hehong
 * @date 2023-11-24
 */
@TableName(value = "system_invite_user", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InviteUserDO extends BaseDO {
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
     * 受邀人用户id
     */
    private Long inviteeUserId;
    /**
     * 受邀状态
     * 枚举 {@link InviteStatusEnum}
     */
    private Integer status;
    /**
     * 多租户编号
     */
    private Long tenantId;
}
