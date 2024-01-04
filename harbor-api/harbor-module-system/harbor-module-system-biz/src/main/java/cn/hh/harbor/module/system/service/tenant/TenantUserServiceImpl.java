package cn.hh.harbor.module.system.service.tenant;

import cn.hh.harbor.framework.common.exception.util.ServiceExceptionUtil;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantUserDO;
import cn.hh.harbor.module.system.dal.mysql.tenant.TenantUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.hh.harbor.module.system.enums.ErrorCodeConstants.TENANT_USER_EXISTS;

@Service
public class TenantUserServiceImpl implements TenantUserService {
    @Resource
    private TenantUserMapper tenantUserMapper;

    @Override
    public void insert(Long tenantId, Long userId) {
        TenantUserDO tenantUserDO = tenantUserMapper.selectByTenantIdAndUserId(tenantId, userId);
        if (tenantUserDO != null) {
            throw ServiceExceptionUtil.exception(TENANT_USER_EXISTS);
        }
        tenantUserMapper.insert(new TenantUserDO().setTenantId(tenantId).setUserId(userId));
    }

    @Override
    public TenantUserDO get(Long tenantId, Long userId) {
        return tenantUserMapper.selectByTenantIdAndUserId(tenantId, userId);
    }
}
