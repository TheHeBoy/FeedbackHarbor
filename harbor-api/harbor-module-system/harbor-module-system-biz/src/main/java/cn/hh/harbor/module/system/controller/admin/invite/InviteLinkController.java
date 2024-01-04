package cn.hh.harbor.module.system.controller.admin.invite;

import cn.hh.harbor.framework.common.pojo.CommonResult;
import cn.hh.harbor.framework.security.core.util.SecurityFrameworkUtils;
import cn.hh.harbor.module.system.controller.admin.invite.vo.link.InviteSendMailReqVO;
import cn.hh.harbor.module.system.service.invite.InviteLinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@Tag(name = "管理后台 - 团队链接邀请")
@RestController
@RequestMapping("/system/invite/link")
@Validated
public class InviteLinkController {

    @Resource
    private InviteLinkService inviteLinkService;

    @Operation(summary = "创建链接")
    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermission('system:invite:invite')")
    public CommonResult<String> create(@NotNull @RequestParam(value = "tenantId") Long tenantId) {
        return CommonResult.success(inviteLinkService.createLink(tenantId));
    }


    @Operation(summary = "得到链接标识")
    @GetMapping("/get")
    @PreAuthorize("@ss.hasPermission('system:invite:invite')")
    public CommonResult<String> get(@NotNull @RequestParam(value = "tenantId") Long tenantId) {
        return CommonResult.success(inviteLinkService.getLink(tenantId));
    }


    @Operation(summary = "删除链接标识")
    @DeleteMapping("/delete")
    @PreAuthorize("@ss.hasPermission('system:invite:invite')")
    public CommonResult<Boolean> delete(@NotNull @RequestParam(value = "code") String code) {
        inviteLinkService.deleteByCode(code);
        return CommonResult.success(true);
    }

    @Operation(summary = "加入管理团队")
    @PostMapping("/join")
    public CommonResult<Boolean> joinByCode(@NotNull @RequestParam(value = "code") String code) {
        inviteLinkService.joinByCode(code, SecurityFrameworkUtils.getLoginUserId());
        return CommonResult.success(true);
    }

    @Operation(summary = "发送邀请邮件")
    @PostMapping("/send-invite-mail")
    @PreAuthorize("@ss.hasPermission('system:invite:invite')")
    public CommonResult<Boolean> sendInviteMail(@RequestBody @Validated InviteSendMailReqVO reqVO) {
        inviteLinkService.sendInviteMail(reqVO, SecurityFrameworkUtils.getLoginUserId());
        return CommonResult.success(true);
    }
}