package cn.hh.harbor.module.system.controller.admin.invite;

import cn.hh.harbor.framework.common.pojo.CommonResult;
import cn.hh.harbor.framework.tenant.core.context.TenantContextHolder;
import cn.hh.harbor.module.system.controller.admin.invite.vo.InviteReplyListRespVO;
import cn.hh.harbor.module.system.controller.admin.invite.vo.InviteUserReqVO;
import cn.hh.harbor.module.system.dal.dataobject.invite.InviteDO;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantDO;
import cn.hh.harbor.module.system.dal.dataobject.user.UserDO;
import cn.hh.harbor.module.system.service.invite.InviteService;
import cn.hh.harbor.module.system.service.tenant.TenantService;
import cn.hh.harbor.module.system.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import static cn.hh.harbor.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 团队邀请")
@RestController
@RequestMapping("/system/invite")
@Validated
public class InviteController {

    @Resource
    private InviteService inviteService;

    @Resource
    private UserService userService;

    @Resource
    private TenantService tenantService;

    @Operation(summary = "邀请用户")
    @PostMapping("/invite-user")
    @PreAuthorize("@ss.hasPermission('system:invite:invite-user')")
    public CommonResult<Boolean> inviteUser(@Valid @RequestBody InviteUserReqVO reqVO) {
        inviteService.inviteUser(reqVO, TenantContextHolder.getTenantId());
        return CommonResult.success(true);
    }

    @Operation(summary = "查看登录用户收到的待回复用户邀请")
    @GetMapping("/list-replay")
    public CommonResult<List<InviteReplyListRespVO>> selectListByInviteeUserId() {
        List<InviteDO> inviteDOS = inviteService.selectListByInviteeUserId(getLoginUserId());

        List<InviteReplyListRespVO> result = inviteDOS.stream().map(inviteDO -> {
            InviteReplyListRespVO respVO = new InviteReplyListRespVO();
            respVO.setId(inviteDO.getId());
            // 填充用户信息
            UserDO user = userService.getUser(inviteDO.getInviterUserId());
            respVO.setNickname(user.getNickname()).setAvatar(user.getAvatar());
            // 填充租户信息
            TenantDO tenant = tenantService.getTenant(inviteDO.getTenantId());
            respVO.setTenantName(tenant.getName()).setTenantLogo(tenant.getLogo());
            return respVO;
        }).collect(Collectors.toList());

        return CommonResult.success(result);
    }

    @Operation(summary = "同意邀请")
    @PostMapping("/accept")
    public CommonResult<Boolean> accept(@NotNull @RequestParam(value = "id") Long id) {
        inviteService.acceptUserInvite(id);
        return CommonResult.success(true);
    }

    @Operation(summary = "拒绝邀请")
    @PutMapping("/refuse")
    public CommonResult<Boolean> refuse(@NotNull @RequestParam(value = "id") Long id) {
        inviteService.refuseUserInvite(id);
        return CommonResult.success(true);
    }
}