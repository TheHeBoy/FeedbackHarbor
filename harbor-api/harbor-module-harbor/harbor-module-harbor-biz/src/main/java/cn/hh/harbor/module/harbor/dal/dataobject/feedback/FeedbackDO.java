package cn.hh.harbor.module.harbor.dal.dataobject.feedback;

import cn.hh.harbor.framework.mybatis.core.type.StringListTypeHandler;
import cn.hh.harbor.framework.tenant.core.db.TenantBaseDO;
import cn.hh.harbor.module.harbor.enums.feedback.FeedbackReplyStateEnum;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

import java.util.List;

/**
 * 用户反馈 DO
 */
@TableName(value = "harbor_feedback", autoResultMap = true)
@KeySequence("harbor_feedback_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDO extends TenantBaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 内容
     */
    private String content;
    /**
     * 点赞数
     */
    private Long likes;
    /**
     * 反馈标签id
     */
    private Long feedbackTagId;
    /**
     * 图片集
     */
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> imgs;
    /**
     * 反馈回复状态
     * 枚举 {@link FeedbackReplyStateEnum}
     */
    private Integer replyState;
    /**
     * 用户id
     */
    private Long uid;
}
