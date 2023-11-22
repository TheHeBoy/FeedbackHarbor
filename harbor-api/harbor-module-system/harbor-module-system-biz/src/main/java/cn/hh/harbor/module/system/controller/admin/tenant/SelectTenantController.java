package cn.hh.harbor.module.system.controller.admin.tenant;

import cn.hh.harbor.framework.common.pojo.CommonResult;
import cn.hh.harbor.framework.security.config.SecurityProperties;
import cn.hh.harbor.framework.security.core.util.SecurityFrameworkUtils;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.selecttenant.SelectTenantCreateReqVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.selecttenant.SelectTenantRespVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.selecttenant.SelectTenantUpdateReqVO;
import cn.hh.harbor.module.system.convert.tenant.TenantConvert;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantDO;
import cn.hh.harbor.module.system.service.tenant.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static cn.hh.harbor.framework.common.pojo.CommonResult.success;
import static cn.hh.harbor.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 租户选择")
@RestController
@RequestMapping("/system/select-tenant")
public class SelectTenantController {
    @Resource
    private TenantService tenantService;

    @Resource
    private SecurityProperties securityProperties;

    @GetMapping("/listByUser")
    @Operation(summary = "通过用户得到所有社区租户")
    public CommonResult<List<SelectTenantRespVO>> listByUser() {
        List<TenantDO> list = tenantService.getTenantList(getLoginUserId());
        return success(TenantConvert.INSTANCE.convertListSelect(list));
    }

    @PostMapping("/create")
    @Operation(summary = "创建租户")
    public CommonResult<Long> createTenant(@Valid @RequestBody SelectTenantCreateReqVO createReqVO, HttpServletRequest request) {
        String token = SecurityFrameworkUtils.obtainAuthorization(request, securityProperties.getTokenHeader());
        return success(tenantService.createTenant(createReqVO, getLoginUserId(), token));
    }

    @PutMapping("/update")
    @Operation(summary = "更新租户")
    public CommonResult<Boolean> updateTenant(@Valid @RequestBody SelectTenantUpdateReqVO updateReqVO) {
        tenantService.updateTenant(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除租户")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteTenant(@RequestParam("id") Long id) {
        tenantService.deleteTenant(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得租户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<SelectTenantRespVO> getTenant(@RequestParam("id") Long id) {
        TenantDO tenant = tenantService.getTenant(id);
        return success(TenantConvert.INSTANCE.convertSelect(tenant));
    }
}
