package cn.iocoder.yudao.module.uservoice.dal.dataobject.comment;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 评论 DO
 *
 *  hehong
 */
@TableName("uservoice_comment")
@KeySequence("uservoice_comment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDO extends BaseDO {

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
     * 用户id
     */
    private Long uid;
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
    /**
     * 用户类型;
     */
    private Integer userType;
    /**
     * 头像;
     */
    private String avatar;
    /**
     * 用户昵称;
     */
    private String nickname;

}
