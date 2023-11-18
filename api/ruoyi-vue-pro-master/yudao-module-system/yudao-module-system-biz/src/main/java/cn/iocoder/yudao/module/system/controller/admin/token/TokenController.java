package cn.iocoder.yudao.module.system.controller.admin.token;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.token.vo.AccessTokenPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.token.vo.AccessTokenRespVO;
import cn.iocoder.yudao.module.system.convert.token.TokenConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.token.TokenAccessDO;
import cn.iocoder.yudao.module.system.enums.logger.LoginLogTypeEnum;
import cn.iocoder.yudao.module.system.service.auth.AuthService;
import cn.iocoder.yudao.module.system.service.token.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - Token 令牌")
@RestController
@RequestMapping("/system/token")
public class TokenController {

    @Resource
    private TokenService tokenService;
    @Resource
    private AuthService authService;

    @GetMapping("/page")
    @Operation(summary = "获得访问令牌分页", description = "只返回有效期内的")
    @PreAuthorize("@ss.hasPermission('system:token:page')")
    public CommonResult<PageResult<AccessTokenRespVO>> getAccessTokenPage(@Valid AccessTokenPageReqVO reqVO) {
        PageResult<TokenAccessDO> pageResult = tokenService.getAccessTokenPage(reqVO);
        return success(TokenConvert.INSTANCE.convert(pageResult));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除访问令牌")
    @Parameter(name = "accessToken", description = "访问令牌", required = true, example = "tudou")
    @PreAuthorize("@ss.hasPermission('system:token:delete')")
    public CommonResult<Boolean> deleteAccessToken(@RequestParam("accessToken") String accessToken) {
        authService.logout(accessToken, LoginLogTypeEnum.LOGOUT_DELETE.getType());
        return success(true);
    }

}
