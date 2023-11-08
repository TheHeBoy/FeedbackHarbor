package cn.iocoder.yudao.module.harbor.controller.app.auth;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.framework.security.config.SecurityProperties;
import cn.iocoder.yudao.framework.security.core.annotations.PreAuthenticated;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.harbor.controller.app.auth.vo.AppAuthLoginReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.auth.vo.AppAuthLoginRespVO;
import cn.iocoder.yudao.module.harbor.controller.app.auth.vo.AppAuthSocialLoginReqVO;
import cn.iocoder.yudao.module.harbor.controller.app.auth.vo.AppAuthUserInfoRespVO;
import cn.iocoder.yudao.module.harbor.convert.auth.AuthConvert;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;
import cn.iocoder.yudao.module.harbor.service.appuser.AppUserService;
import cn.iocoder.yudao.module.harbor.service.auth.AppAuthService;
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
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 ADMIN - 认证")
@RestController
@RequestMapping("/harbor/auth")
@Validated
@Slf4j
public class AppAuthController {

    @Resource
    private AppAuthService appAuthService;

    @Resource
    private AppUserService appUserService;

    @Resource
    private SecurityProperties securityProperties;

    @PostMapping("/login")
    @Operation(summary = "使用账号 + 密码登录")
    public CommonResult<AppAuthLoginRespVO> login(@RequestBody @Valid AppAuthLoginReqVO reqVO) {
        return success(appAuthService.login(reqVO));
    }

    @PostMapping("/logout")
    @Operation(summary = "登出系统")
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = SecurityFrameworkUtils.obtainAuthorization(request, securityProperties.getTokenHeader());
        if (StrUtil.isNotBlank(token)) {
            appAuthService.logout(token);
        }
        return success(true);
    }

    @GetMapping("/get-user-info")
    @PreAuthenticated
    @Operation(summary = "获取登录用用户信息")
    public CommonResult<AppAuthUserInfoRespVO> getUserInfo() {
        // 1.1 获得用户信息
        AppUserDO user = appUserService.getUser(getLoginUserId());
        if (user == null) {
            return null;
        }

        // 2. 拼接结果返回
        return success(AuthConvert.INSTANCE.convert(user));
    }

    @GetMapping("/social-auth-redirect")
    @PermitAll
    @Operation(summary = "社交授权的跳转")
    @Parameters({
            @Parameter(name = "type", description = "社交类型", required = true),
            @Parameter(name = "redirectUri", description = "回调路径")
    })
    public CommonResult<String> socialLogin(@RequestParam("type") Integer type,
                                            @RequestParam("redirectUri") String redirectUri) {
        return CommonResult.success(appAuthService.getAuthorizeUrl(type, redirectUri));
    }

    @RequestMapping("/social-login")
    @PermitAll
    @Operation(summary = "社交快捷登录，使用 code 授权码")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<AppAuthLoginRespVO> socialQuickLogin(@RequestBody @Valid AppAuthSocialLoginReqVO reqVO) {
        return success(appAuthService.socialLogin(reqVO));
    }
}
