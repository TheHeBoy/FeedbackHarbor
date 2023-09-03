package cn.iocoder.yudao.module.uservoice.controller.app.auth;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.security.config.SecurityProperties;
import cn.iocoder.yudao.framework.security.core.annotations.PreAuthenticated;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.uservoice.controller.app.auth.vo.AppAuthLoginReqVO;
import cn.iocoder.yudao.module.uservoice.controller.app.auth.vo.AppAuthLoginRespVO;
import cn.iocoder.yudao.module.uservoice.controller.app.auth.vo.AppAuthUserInfoRespVO;
import cn.iocoder.yudao.module.uservoice.convert.auth.AuthConvert;
import cn.iocoder.yudao.module.uservoice.dal.dataobject.appuser.AppUserDO;
import cn.iocoder.yudao.module.uservoice.service.appuser.AppUserService;
import cn.iocoder.yudao.module.uservoice.service.auth.AppAuthService;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "用户 APP - 认证")
@RestController
@RequestMapping("/uservoice/auth")
@Validated
@Slf4j
public class AppAuthController {

    @Resource
    private AppAuthService authService;

    @Resource
    private AppUserService appUserService;

    @Resource
    private SecurityProperties securityProperties;


    @PostMapping("/login")
    @PermitAll
    @Operation(summary = "使用账号 + 密码登录")
    public CommonResult<AppAuthLoginRespVO> login(@RequestBody @Valid AppAuthLoginReqVO reqVO) {
        return success(authService.login(reqVO));
    }

    @PostMapping("/logout")
    @PermitAll
    @Operation(summary = "登出系统")
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = SecurityFrameworkUtils.obtainAuthorization(request, securityProperties.getTokenHeader());
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token);
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
}
