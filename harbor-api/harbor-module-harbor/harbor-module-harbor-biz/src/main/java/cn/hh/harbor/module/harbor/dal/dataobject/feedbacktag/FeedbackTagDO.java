package cn.hh.harbor.module.harbor.dal.dataobject.feedbacktag;

import cn.hh.harbor.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.hh.harbor.framework.mybatis.core.dataobject.BaseDO;

/**
 * 反馈标签 DO
 *
 *  hehong
 */
@TableName(value = "harbor_feedback_tag",autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackTagDO extends TenantBaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 标签名中文
     */
    private String nameCh;
    /**
     * 标签名英语
     */
    private String nameEn;
    /**
     * 标签顺序
     */
    private Integer sort;
    /**
     * 标签颜色
     */
    private String color;

}
