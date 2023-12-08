package cn.hh.harbor.module.system.api.tenant;

import cn.hh.harbor.module.system.api.tenant.dto.TenantRespDTO;
import cn.hh.harbor.module.system.convert.tenant.TenantConvert;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantUserDO;
import cn.hh.harbor.module.system.service.tenant.TenantService;
import cn.hh.harbor.module.system.service.tenant.TenantUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 多租户的 API 实现类
 */
@Service
public class TenantApiImpl implements TenantApi {

    @Resource
    private TenantService tenantService;

    @Resource
    private TenantUserService tenantUserService;

    @Override
    public List<Long> getTenantIdList() {
        return tenantService.getTenantIdList();
    }

    @Override
    public void validateTenant(Long id) {
        tenantService.validTenant(id);
    }

    @Override
    public TenantRespDTO checkTenantRouterUri(String routerUri) {
        return TenantConvert.INSTANCE.convertDTO(tenantService.checkTenantRouterUri(routerUri));
    }

    @Override
    public boolean checkTenantUser(Long tenantId, Long userId) {
        return tenantUserService.get(userId, tenantId) != null;
    }

}
