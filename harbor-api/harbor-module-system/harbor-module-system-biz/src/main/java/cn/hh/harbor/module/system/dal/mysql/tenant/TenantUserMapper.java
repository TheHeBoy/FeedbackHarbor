package cn.hh.harbor.module.system.dal.mysql.tenant;

import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.framework.mybatis.core.mapper.BaseMapperX;
import cn.hh.harbor.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.tenant.TenantExportReqVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.tenant.TenantPageReqVO;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantDO;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantUserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 租户和用户关联 Mapper
 */
@Mapper
public interface TenantUserMapper extends BaseMapperX<TenantUserDO> {


    default int deleteBatchByTenantId(Long tenantId) {
        return delete(new LambdaQueryWrapperX<TenantUserDO>().eq(
                TenantUserDO::getTenantId, tenantId
        ));
    }

    default int deleteByTenantIdAndUserId(Long tenantId, Long userId) {
        return delete(new LambdaQueryWrapperX<TenantUserDO>()
                .eq(TenantUserDO::getTenantId, tenantId)
                .eq(TenantUserDO::getUserId, userId)
        );
    }

    default TenantUserDO selectByTenantIdAndUserId(Long tenantId, Long userId) {
        return selectOne(TenantUserDO::getTenantId, tenantId,
                TenantUserDO::getUserId, userId);
    }
}
