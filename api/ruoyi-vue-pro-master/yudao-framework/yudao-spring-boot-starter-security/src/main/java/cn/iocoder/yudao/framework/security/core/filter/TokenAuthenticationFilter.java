package cn.iocoder.yudao.framework.security.core.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.servlet.ServletUtils;
import cn.iocoder.yudao.framework.security.config.SecurityProperties;
import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.framework.web.core.handler.GlobalExceptionHandler;
import cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils;
import cn.iocoder.yudao.module.harbor.api.appuser.AppUserApi;
import cn.iocoder.yudao.module.harbor.api.appuser.dto.AppUserRespDTO;
import cn.iocoder.yudao.module.system.api.token.TokenApi;
import cn.iocoder.yudao.module.system.api.token.dto.TokenCheckRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.iocoder.yudao.framework.common.enums.UserTypeEnum.ADMIN;
import static cn.iocoder.yudao.framework.common.enums.UserTypeEnum.APP;

/**
 * Token 过滤器，验证 token 的有效性
 * 验证通过后，获得 {@link LoginUser} 信息，并加入到 Spring Security 上下文
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    private final GlobalExceptionHandler globalExceptionHandler;

    private final TokenApi tokenApi;

    private final AppUserApi appUserApi;

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = SecurityFrameworkUtils.obtainAuthorization(request, securityProperties.getTokenHeader());
        if (StrUtil.isNotEmpty(token)) {
            Integer userType = WebFrameworkUtils.getLoginUserType(request);
            try {
                // 1 基于 token 构建登录用户
                LoginUser loginUser = buildLoginUserByToken(token, userType);

                // 2. 设置当前用户
                if (loginUser != null) {
                    SecurityFrameworkUtils.setLoginUser(loginUser, request);
                }
            } catch (Throwable ex) {
                CommonResult<?> result = globalExceptionHandler.allExceptionHandler(request, ex);
                ServletUtils.writeJSON(response, result);
                return;
            }
        }

        // 继续过滤链
        chain.doFilter(request, response);
    }

    private LoginUser buildLoginUserByToken(String token, Integer userType) {
        try {
            TokenCheckRespDTO accessToken = tokenApi.checkAccessToken(token);
            if (accessToken == null) {
                return null;
            }
            // 管理员访问 App 的请求路径时，登录信息转换为 App User 信息
            if (ObjectUtil.equal(accessToken.getUserType(), ADMIN.getValue()) && ObjectUtil.equal(userType, APP.getValue())) {
                AppUserRespDTO appUser = appUserApi.getAppUserByAdmin(accessToken.getUserId());
                if (ObjectUtil.isNull(appUser)) {
                    throw new AccessDeniedException("管理员关联的App用户不存在");
                }
                return new LoginUser().setId(appUser.getId())
                        .setUserType(APP.getValue())
                        .setTenantIds(accessToken.getTenantIds());
            }
            // 用户类型不匹配
            if (ObjectUtil.notEqual(accessToken.getUserType(), userType)) {
                throw new AccessDeniedException("错误的用户类型");
            }
            // 构建登录用户
            return new LoginUser().setId(accessToken.getUserId())
                    .setUserType(accessToken.getUserType())
                    .setTenantIds(accessToken.getTenantIds());
        } catch (ServiceException serviceException) {
            // 校验 Token 不通过时，考虑到一些接口是无需登录的，所以直接返回 null 即可
            return null;
        }
    }
}
