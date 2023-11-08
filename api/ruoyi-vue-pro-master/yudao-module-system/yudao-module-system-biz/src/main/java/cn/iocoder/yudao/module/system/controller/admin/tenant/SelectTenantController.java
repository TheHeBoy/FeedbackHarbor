package cn.iocoder.yudao.module.system.controller.admin.tenant;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.tenant.vo.selecttenant.SelectTenantCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.tenant.vo.selecttenant.SelectTenantRespVO;
import cn.iocoder.yudao.module.system.controller.admin.tenant.vo.selecttenant.SelectTenantUpdateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.tenant.vo.tenant.TenantCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.tenant.vo.tenant.TenantRespVO;
import cn.iocoder.yudao.module.system.controller.admin.tenant.vo.tenant.TenantUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.tenant.TenantConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.tenant.TenantDO;
import cn.iocoder.yudao.module.system.service.tenant.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 租户选择")
@RestController
@RequestMapping("/system/select-tenant")
public class SelectTenantController {
    @Resource
    private TenantService tenantService;

    @GetMapping("/listByUser")
    @Operation(summary = "通过用户得到所有社区租户")
    public CommonResult<List<SelectTenantRespVO>> listByUser() {
        List<TenantDO> list = tenantService.getTenantList(getLoginUserId());
        return success(TenantConvert.INSTANCE.convertListSelect(list));
    }

    @PostMapping("/create")
    @Operation(summary = "创建租户")
    public CommonResult<Long> createTenant(@Valid @RequestBody SelectTenantCreateReqVO createReqVO) {
        return success(tenantService.createTenant(createReqVO, getLoginUserId()));
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
