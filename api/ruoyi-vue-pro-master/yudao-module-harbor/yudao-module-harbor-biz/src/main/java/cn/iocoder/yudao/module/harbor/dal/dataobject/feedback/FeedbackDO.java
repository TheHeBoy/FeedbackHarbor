package cn.iocoder.yudao.module.harbor.dal.dataobject.feedback;

import cn.iocoder.yudao.module.harbor.dal.dataobject.UserBaseDO;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 用户反馈 DO
 *
 *  芋道源码
 */
@TableName("harbor_feedback")
@KeySequence("harbor_feedback_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDO extends UserBaseDO {

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
     * 反馈类型
     */
    private Integer feedbackType;
}
