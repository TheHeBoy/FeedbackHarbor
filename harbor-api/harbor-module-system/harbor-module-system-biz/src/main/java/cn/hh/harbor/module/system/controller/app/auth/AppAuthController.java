package cn.hh.harbor.module.system.controller.app.auth;

import cn.hh.harbor.framework.common.pojo.CommonResult;
import cn.hh.harbor.module.system.api.tenant.TenantApi;
import cn.hh.harbor.module.system.api.tenant.dto.TenantRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.constraints.NotNull;

import static cn.hh.harbor.framework.common.pojo.CommonResult.success;

@Tag(name = "App 用户 - 认证")
@RestController
@RequestMapping("/system/auth")
@Validated
@Slf4j
public class AppAuthController {

    @Resource
    private TenantApi tenantApi;

    @GetMapping("/check-tenantRouterUri")
    @PermitAll
    @Operation(summary = "校验租户名")
    public CommonResult<TenantRespDTO> checkTenantRouterUri(@NotNull String routerUri) {
        return success(tenantApi.checkTenantRouterUri(routerUri));
    }
}
