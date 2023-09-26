package cn.iocoder.yudao.module.harbor.dal.dataobject.like;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import cn.iocoder.yudao.module.harbor.enums.appuser.AppUserTypeEnum;
import cn.iocoder.yudao.module.harbor.enums.like.LikeBusTypeEnum;
import cn.iocoder.yudao.module.harbor.enums.like.LikeStateEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 点赞表 DO
 *
 *  hehong
 */
@TableName("harbor_like")
@KeySequence("harbor_like_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeDO extends TenantBaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 关联id
     */
    private Long rid;
    /**
     * 用户id
     */
    private Long uid;
    /**
     * 点赞状态
     * 枚举 {@link LikeStateEnum}
     */
    private int state;
    /**
     * 业务类型
     * 枚举 {@link LikeBusTypeEnum}
     */
    private int busType;
}
