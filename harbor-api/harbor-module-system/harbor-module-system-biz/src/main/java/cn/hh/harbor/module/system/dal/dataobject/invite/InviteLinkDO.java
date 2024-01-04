package cn.hh.harbor.module.system.dal.dataobject.invite;

import cn.hh.harbor.framework.common.enums.CommonStatusEnum;
import cn.hh.harbor.framework.mybatis.core.dataobject.BaseDO;
import cn.hh.harbor.module.system.enums.invite.link.InviteLinkTypeEnum;
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
@TableName(value = "system_invite_link", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InviteLinkDO extends BaseDO {
    /**
     * 主键
     */
    @TableId(value = "ID")
    private Long id;
    /**
     * 链接标识
     */
    private String code;
    /**
     * 链接状态
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 链接类型
     * 枚举 {@link InviteLinkTypeEnum}
     */
    private Integer type;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    /**
     * 多租户编号
     */
    private Long tenantId;
}
