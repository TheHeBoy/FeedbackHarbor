package cn.hh.harbor.framework.security.core.filter;

import cn.hh.harbor.framework.common.enums.UserTypeEnum;
import cn.hh.harbor.module.system.api.tenant.TenantApi;
import cn.hutool.core.util.StrUtil;
import cn.hh.harbor.framework.common.exception.ServiceException;
import cn.hh.harbor.framework.common.pojo.CommonResult;
import cn.hh.harbor.framework.common.util.servlet.ServletUtils;
import cn.hh.harbor.framework.security.config.SecurityProperties;
import cn.hh.harbor.framework.security.core.LoginUser;
import cn.hh.harbor.framework.security.core.util.SecurityFrameworkUtils;
import cn.hh.harbor.framework.web.core.handler.GlobalExceptionHandler;
import cn.hh.harbor.framework.web.core.util.WebFrameworkUtils;
import cn.hh.harbor.module.system.api.token.TokenApi;
import cn.hh.harbor.module.system.api.token.dto.TokenCheckRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token 过滤器，验证 token 的有效性
 * 验证通过后，获得 {@link LoginUser} 信息，并加入到 Spring Security 上下文
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    private final GlobalExceptionHandler globalExceptionHandler;

    private final TokenApi tokenApi;

    private final TenantApi tenantApi;

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = SecurityFrameworkUtils.obtainAuthorization(request, securityProperties.getTokenHeader());
        if (StrUtil.isNotEmpty(token)) {
            try {
                // 1 基于 token 构建登录用户
                LoginUser loginUser = buildLoginUserByToken(token, WebFrameworkUtils.getTenantId(request));

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

    private LoginUser buildLoginUserByToken(String token, Long tenantId) {
        try {
            TokenCheckRespDTO accessToken = tokenApi.checkAccessToken(token);
            if (accessToken == null) {
                return null;
            }

            Integer userType = tenantApi.checkTenantUser(accessToken.getUserId(), tenantId) ? UserTypeEnum.ADMIN.getValue() : UserTypeEnum.APP.getValue();

            // 构建登录用户
            return new LoginUser().setId(accessToken.getUserId()).setUserType(userType).setTenantIds(accessToken.getTenantIds());
        } catch (ServiceException serviceException) {
            // 校验 Token 不通过时，考虑到一些接口是无需登录的，所以直接返回 null 即可
            return null;
        }
    }
}
