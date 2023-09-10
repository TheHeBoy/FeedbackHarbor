package cn.iocoder.yudao.module.harbor.dal.dataobject.comment;

import cn.iocoder.yudao.module.harbor.dal.dataobject.UserBaseDO;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 评论 DO
 *
 *  hehong
 */
@TableName("harbor_comment")
@KeySequence("harbor_comment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDO extends UserBaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 父节点
     */
    private Long parentId;
    /**
     * 反馈id
     */
    private Long feedbackId;
    /**
     * 内容
     */
    private String content;
    /**
     * 点赞数
     */
    private Long likes;
}
