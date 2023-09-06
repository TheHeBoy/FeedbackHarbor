package cn.iocoder.yudao.module.harbor.controller.admin.appuser;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.harbor.controller.admin.appuser.vo.*;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;
import cn.iocoder.yudao.module.harbor.convert.appuser.AppUserConvert;
import cn.iocoder.yudao.module.harbor.service.appuser.AppUserService;

@Tag(name = "管理后台 - App用户")
@RestController
@RequestMapping("/harbor/app-user")
@Validated
public class AppUserController {

    @Resource
    private AppUserService appUserService;

    @PostMapping("/create")
    @Operation(summary = "创建App用户")
    @PreAuthorize("@ss.hasPermission('uservoice:app-user:create')")
    public CommonResult<Long> createAppUser(@Valid @RequestBody AppUserCreateReqVO createReqVO) {
        return success(appUserService.createAppUser(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新App用户")
    @PreAuthorize("@ss.hasPermission('uservoice:app-user:update')")
    public CommonResult<Boolean> updateAppUser(@Valid @RequestBody AppUserUpdateReqVO updateReqVO) {
        appUserService.updateAppUser(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除App用户")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('uservoice:app-user:delete')")
    public CommonResult<Boolean> deleteAppUser(@RequestParam("id") Long id) {
        appUserService.deleteAppUser(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得App用户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('uservoice:app-user:query')")
    public CommonResult<AppUserRespVO> getAppUser(@RequestParam("id") Long id) {
        AppUserDO appUser = appUserService.getAppUser(id);
        return success(AppUserConvert.INSTANCE.convert(appUser));
    }

    @GetMapping("/list")
    @Operation(summary = "获得App用户列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('uservoice:app-user:query')")
    public CommonResult<List<AppUserRespVO>> getAppUserList(@RequestParam("ids") Collection<Long> ids) {
        List<AppUserDO> list = appUserService.getAppUserList(ids);
        return success(AppUserConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得App用户分页")
    @PreAuthorize("@ss.hasPermission('uservoice:app-user:query')")
    public CommonResult<PageResult<AppUserRespVO>> getAppUserPage(@Valid AppUserPageReqVO pageVO) {
        PageResult<AppUserDO> pageResult = appUserService.getAppUserPage(pageVO);
        return success(AppUserConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出App用户 Excel")
    @PreAuthorize("@ss.hasPermission('uservoice:app-user:export')")
    @OperateLog(type = EXPORT)
    public void exportAppUserExcel(@Valid AppUserExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<AppUserDO> list = appUserService.getAppUserList(exportReqVO);
        // 导出 Excel
        List<AppUserExcelVO> datas = AppUserConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "App用户.xls", "数据", AppUserExcelVO.class, datas);
    }

}
