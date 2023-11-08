package cn.iocoder.yudao.module.system.dal.mysql.tenant;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.tenant.vo.tenant.TenantExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.tenant.vo.tenant.TenantPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.tenant.TenantDO;
import cn.iocoder.yudao.module.system.dal.dataobject.tenant.TenantUserDO;
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
}
