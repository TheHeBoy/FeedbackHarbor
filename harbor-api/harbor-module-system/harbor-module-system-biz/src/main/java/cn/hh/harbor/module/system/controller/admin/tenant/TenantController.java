package cn.hh.harbor.module.system.controller.admin.tenant;

import cn.hh.harbor.framework.common.pojo.CommonResult;
import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.framework.excel.core.util.ExcelUtils;
import cn.hh.harbor.framework.operatelog.core.annotations.OperateLog;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.selecttenant.SelectTenantUpdateReqVO;
import cn.hh.harbor.module.system.controller.admin.tenant.vo.tenant.*;
import cn.hh.harbor.module.system.convert.tenant.TenantConvert;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantDO;
import cn.hh.harbor.module.system.service.tenant.TenantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.hh.harbor.framework.common.pojo.CommonResult.success;
import static cn.hh.harbor.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.hh.harbor.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 租户")
@RestController
@RequestMapping("/system/tenant")
public class TenantController {

    @Resource
    private TenantService tenantService;

    @DeleteMapping("/delete")
    @Operation(summary = "删除租户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:tenant:delete')")
    public CommonResult<Boolean> deleteTenant(@RequestParam("id") Long id) {
        tenantService.deleteTenant(id);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新租户")
    public CommonResult<Boolean> updateTenant(@Valid @RequestBody TenantUpdateReqVO updateReqVO) {
        tenantService.updateTenant(updateReqVO);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得租户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:tenant:query')")
    public CommonResult<TenantRespVO> getTenant(@RequestParam("id") Long id) {
        TenantDO tenant = tenantService.getTenant(id);
        return success(TenantConvert.INSTANCE.convert(tenant));
    }

    @GetMapping("/page")
    @Operation(summary = "获得租户分页")
    @PreAuthorize("@ss.hasPermission('system:tenant:query')")
    public CommonResult<PageResult<TenantRespVO>> getTenantPage(@Valid TenantPageReqVO pageVO) {
        PageResult<TenantDO> pageResult = tenantService.getTenantPage(pageVO);
        return success(TenantConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出租户 Excel")
    @PreAuthorize("@ss.hasPermission('system:tenant:export')")
    @OperateLog(type = EXPORT)
    public void exportTenantExcel(@Valid TenantExportReqVO exportReqVO,
                                  HttpServletResponse response) throws IOException {
        List<TenantDO> list = tenantService.getTenantList(exportReqVO);
        // 导出 Excel
        List<TenantExcelVO> datas = TenantConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "租户.xls", "数据", TenantExcelVO.class, datas);
    }


}
