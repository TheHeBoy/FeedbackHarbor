package cn.hh.harbor.module.system.controller.app.auth;

import cn.hutool.core.util.StrUtil;
import cn.hh.harbor.framework.common.enums.UserTypeEnum;
import cn.hh.harbor.framework.common.pojo.CommonResult;
import cn.hh.harbor.framework.operatelog.core.annotations.OperateLog;
import cn.hh.harbor.framework.security.config.SecurityProperties;
import cn.hh.harbor.framework.security.core.annotations.PreAuthenticated;
import cn.hh.harbor.framework.security.core.util.SecurityFrameworkUtils;
import cn.hh.harbor.module.system.api.tenant.TenantApi;
import cn.hh.harbor.module.system.api.tenant.dto.TenantRespDTO;
import cn.hh.harbor.module.system.controller.app.auth.vo.AppAuthLoginReqVO;
import cn.hh.harbor.module.system.controller.app.auth.vo.AppAuthLoginRespVO;
import cn.hh.harbor.module.system.controller.app.auth.vo.AppAuthSocialLoginReqVO;
import cn.hh.harbor.module.system.controller.app.auth.vo.AppAuthUserInfoRespVO;
import cn.hh.harbor.module.system.convert.auth.AuthConvert;
import cn.hh.harbor.module.system.dal.dataobject.token.TokenAccessDO;
import cn.hh.harbor.module.system.dal.dataobject.user.UserDO;
import cn.hh.harbor.module.system.enums.logger.LoginLogTypeEnum;
import cn.hh.harbor.module.system.service.auth.AuthService;
import cn.hh.harbor.module.system.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static cn.hh.harbor.framework.common.pojo.CommonResult.success;
import static cn.hh.harbor.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "App 用户 - 认证")
@RestController
@RequestMapping("/system/auth")
@Validated
@Slf4j
public class AppAuthController {

    @Resource
    private AuthService authService;

    @Resource
    private UserService userService;

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private TenantApi tenantApi;

    @PostMapping("/login")
    @PermitAll
    @Operation(summary = "使用账号 + 密码登录")
    public CommonResult<AppAuthLoginRespVO> login(@RequestBody @Valid AppAuthLoginReqVO reqVO) {
        TokenAccessDO tokenAccessDO = authService.login(reqVO.getUsername(), reqVO.getPassword(), getUserType());
        return success(AuthConvert.INSTANCE.convertApp(tokenAccessDO));
    }

    @PostMapping("/logout")
    @PermitAll
    @Operation(summary = "登出系统")
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = SecurityFrameworkUtils.obtainAuthorization(request, securityProperties.getTokenHeader());
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token, LoginLogTypeEnum.LOGOUT_SELF.getType());
        }
        return success(true);
    }

    @GetMapping("/check-tenantRouterUri")
    @PermitAll
    @Operation(summary = "校验租户名")
    public CommonResult<TenantRespDTO> checkTenantRouterUri(@NotNull String routerUri) {
        return success(tenantApi.checkTenantRouterUri(routerUri));
    }

    @GetMapping("/get-user-info")
    @PreAuthenticated
    @Operation(summary = "获取登录用用户信息")
    public CommonResult<AppAuthUserInfoRespVO> getUserInfo() {
        UserDO user = userService.getUser(getLoginUserId());
        return success(AuthConvert.INSTANCE.convertApp(user));
    }

    @GetMapping("/social-auth-redirect")
    @PermitAll
    @Operation(summary = "社交授权的跳转")
    @Parameters({
            @Parameter(name = "type", description = "社交类型", required = true),
            @Parameter(name = "redirectUri", description = "回调路径")
    })
    public CommonResult<String> socialAuthRedirect(@RequestParam("type") Integer type,
                                            @RequestParam("redirectUri") String redirectUri) {
        return CommonResult.success(authService.getAuthorizeUrl(type, redirectUri));
    }

    @RequestMapping("/social-login")
    @PermitAll
    @Operation(summary = "社交登录，使用 code 授权码")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<AppAuthLoginRespVO> socialLogin(@RequestBody @Valid AppAuthSocialLoginReqVO reqVO) {
        return CommonResult.success(AuthConvert.INSTANCE.convertApp(authService.socialLogin(reqVO, getUserType())));
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.APP;
    }
}
