package cn.iocoder.yudao.module.system.api.tenant;

import cn.iocoder.yudao.module.system.api.tenant.dto.TenantRespDTO;
import cn.iocoder.yudao.module.system.convert.tenant.TenantConvert;
import cn.iocoder.yudao.module.system.service.tenant.TenantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 多租户的 API 实现类
 *
 *
 */
@Service
public class TenantApiImpl implements TenantApi {

    @Resource
    private TenantService tenantService;

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

}
