package cn.hh.harbor.module.system.controller.admin.auth;

import cn.hh.harbor.framework.common.util.servlet.ServletUtils;
import cn.hh.harbor.module.system.enums.mail.MailCaptchaSceneEnum;
import cn.hh.harbor.module.system.service.mail.MailCaptchaService;
import cn.hh.harbor.module.system.service.mail.vo.MailCaptchaSendReqVO;
import cn.hutool.core.util.StrUtil;
import cn.hh.harbor.framework.common.enums.CommonStatusEnum;
import cn.hh.harbor.framework.common.enums.UserTypeEnum;
import cn.hh.harbor.framework.common.pojo.CommonResult;
import cn.hh.harbor.framework.operatelog.core.annotations.OperateLog;
import cn.hh.harbor.framework.security.config.SecurityProperties;
import cn.hh.harbor.module.system.controller.admin.auth.vo.*;
import cn.hh.harbor.module.system.controller.admin.user.vo.user.UserLoginInfoRespVO;
import cn.hh.harbor.module.system.convert.auth.AuthConvert;
import cn.hh.harbor.module.system.convert.user.UserConvert;
import cn.hh.harbor.module.system.dal.dataobject.permission.MenuDO;
import cn.hh.harbor.module.system.dal.dataobject.permission.RoleDO;
import cn.hh.harbor.module.system.dal.dataobject.token.TokenAccessDO;
import cn.hh.harbor.module.system.dal.dataobject.user.UserDO;
import cn.hh.harbor.module.system.enums.logger.LoginLogTypeEnum;
import cn.hh.harbor.module.system.service.auth.AuthService;
import cn.hh.harbor.module.system.service.permission.MenuService;
import cn.hh.harbor.module.system.service.permission.PermissionService;
import cn.hh.harbor.module.system.service.permission.RoleService;
import cn.hh.harbor.module.system.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

import static cn.hh.harbor.framework.common.pojo.CommonResult.success;
import static cn.hh.harbor.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.hh.harbor.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.hh.harbor.framework.security.core.util.SecurityFrameworkUtils.obtainAuthorization;

@Tag(name = "管理后台 - 认证")
@RestController
@RequestMapping("/system/auth")
@Validated
@Slf4j
public class AuthController {

    @Resource
    private AuthService authService;
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private MailCaptchaService mailCaptchaService;

    @PostMapping("/login")
    @PermitAll
    @Operation(summary = "使用账号密码登录")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        TokenAccessDO tokenAccessDO = authService.login(reqVO.getUsername(), reqVO.getPassword());
        return success(AuthConvert.INSTANCE.convert(tokenAccessDO));
    }

    @PostMapping("/logout")
    @PermitAll
    @Operation(summary = "登出系统")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = obtainAuthorization(request, securityProperties.getTokenHeader());
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token, LoginLogTypeEnum.LOGOUT_SELF.getType());
        }
        return success(true);
    }

    @GetMapping("/get-permission-info")
    @Operation(summary = "获取登录用户的权限信息")
    public CommonResult<AuthPermissionInfoRespVO> getPermissionInfo() {
        // 1 获得角色列表
        Set<Long> roleIds = permissionService.getUserRoleIdListByUserId(getLoginUserId());
        List<RoleDO> roles = roleService.getRoleList(roleIds);
        roles.removeIf(role -> !CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus())); // 移除禁用的角色

        // 2 获得菜单列表
        Set<Long> menuIds = permissionService.getRoleMenuListByRoleId(convertSet(roles, RoleDO::getId));
        List<MenuDO> menuList = menuService.getMenuList(menuIds);
        menuList.removeIf(menu -> !CommonStatusEnum.ENABLE.getStatus().equals(menu.getStatus())); // 移除禁用的菜单

        // 3. 拼接结果返回
        return success(AuthConvert.INSTANCE.convert(roles, menuList));
    }

    @GetMapping("/login-user-info")
    @Operation(summary = "获取登录用户息")
    public CommonResult<UserLoginInfoRespVO> getLoginUserinfo() {
        UserDO user = userService.getUser(getLoginUserId());
        return success(UserConvert.INSTANCE.convert5(user));
    }

    // ========== 注册相关 ==========

    @PostMapping("/mail-register")
    @PermitAll
    @Operation(summary = "邮箱注册")
    public CommonResult<Boolean> mailRegister(@RequestBody @Valid AuthMailRegisterReqVO reqVO) {
        authService.mailRegister(reqVO);
        return success(true);
    }

    @PostMapping("/send-register-mail-captcha")
    @PermitAll
    @Operation(summary = "发送邮箱注册验证码")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<Boolean> sendRegisterMailCaptcha(@NotNull @Email @RequestParam(value = "mail") String mail) {
        mailCaptchaService.sendMailCaptcha(new MailCaptchaSendReqVO()
                .setMail(mail)
                .setScene(MailCaptchaSceneEnum.REGISTER.getScene())
                .setCreateIp(ServletUtils.getClientIP()));
        return success(true);
    }

    // ========== 重置密码相关 ==========

    @GetMapping("/check-username")
    @PermitAll
    @Operation(summary = "检查用户名")
    public CommonResult<AuthCheckUsernameRespVO> checkUsername(@RequestParam(value = "username") @Valid String username) {
        UserDO userDO = userService.getUserByUsername(username);
        AuthCheckUsernameRespVO respVO = new AuthCheckUsernameRespVO();
        if (userDO != null) {
            respVO.setUserId(userDO.getId()).setMail(userDO.getEmail());
        }
        return success(respVO);
    }

    @PostMapping("/send-reset-passwd-mail-captcha")
    @PermitAll
    @Operation(summary = "发送邮箱重置密码验证码")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<Boolean> sendResetPasswdMailCaptcha(@NotNull @Email @RequestParam(value = "mail") String mail) {
        mailCaptchaService.sendMailCaptcha(new MailCaptchaSendReqVO()
                .setMail(mail)
                .setScene(MailCaptchaSceneEnum.RESET_PASSWD.getScene())
                .setCreateIp(ServletUtils.getClientIP()));
        return success(true);
    }

    @PostMapping("/reset-passwd")
    @PermitAll
    @Operation(summary = "重置密码")
    public CommonResult<Boolean> resetPasswd(@RequestBody @Valid AuthResetPasswdReqVO reqVO) {
        authService.resetPasswd(reqVO);
        return success(true);
    }

    // ========== 社交登录相关 ==========

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

    @PostMapping("/social-login")
    @PermitAll
    @Operation(summary = "社交快捷登录，使用 code 授权码")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<AuthLoginRespVO> socialLogin(@RequestBody @Valid AuthSocialLoginReqVO reqVO) {
        return success(AuthConvert.INSTANCE.convert(authService.socialLogin(reqVO)));
    }
}
