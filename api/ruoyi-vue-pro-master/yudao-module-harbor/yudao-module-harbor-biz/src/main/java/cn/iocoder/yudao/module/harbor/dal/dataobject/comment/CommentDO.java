package cn.iocoder.yudao.module.harbor.dal.dataobject.comment;

import cn.iocoder.yudao.framework.mybatis.core.type.StringListTypeHandler;
import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

import java.util.List;

/**
 * 评论 DO
 * <p>
 * hehong
 */
@TableName(value = "harbor_comment",autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDO extends TenantBaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
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
    /**
     * 图片集
     */
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> imgs;
    /**
     * 用户id
     */
    private Long uid;
}
