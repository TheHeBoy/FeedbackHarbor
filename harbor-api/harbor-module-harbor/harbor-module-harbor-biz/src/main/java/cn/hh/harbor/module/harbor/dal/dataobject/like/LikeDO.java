package cn.hh.harbor.module.harbor.dal.dataobject.like;

import cn.hh.harbor.framework.mybatis.core.dataobject.BaseDO;
import cn.hh.harbor.module.harbor.enums.common.BusTypeEnum;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 点赞表 DO
 * <p>
 * hehong
 */
@TableName(value = "harbor_like", autoResultMap = true)
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeDO extends BaseDO {

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
     * 业务类型
     * 枚举 {@link BusTypeEnum}
     */
    private int busType;
}
