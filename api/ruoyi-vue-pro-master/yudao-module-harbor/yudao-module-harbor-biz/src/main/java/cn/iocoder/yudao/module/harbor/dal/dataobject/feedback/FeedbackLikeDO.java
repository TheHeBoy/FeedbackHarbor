package cn.iocoder.yudao.module.harbor.dal.dataobject.feedback;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.harbor.enums.appuser.AppUserTypeEnum;
import cn.iocoder.yudao.module.harbor.enums.feedback.FeedbackLikeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 反馈点赞表 DO
 *
 *  hehong
 */
@TableName("harbor_feedback_like")
@KeySequence("harbor_feedback_like_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackLikeDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 反馈id
     */
    private Long feedbackId;
    /**
     * 用户id
     */
    private Long uid;
    /**
     * 是否点赞
     * 枚举 {@link FeedbackLikeEnum}
     */
    private Integer liked;
}
