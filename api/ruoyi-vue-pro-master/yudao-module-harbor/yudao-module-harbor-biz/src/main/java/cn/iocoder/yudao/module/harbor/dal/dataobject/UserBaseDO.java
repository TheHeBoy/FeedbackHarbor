package cn.iocoder.yudao.module.harbor.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 需要带用户信息的基础类
 * @author hehong
 * @date 2023-09-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class UserBaseDO extends TenantBaseDO {
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
    /**
     * 用户id
     */
    private Long uid;
}
