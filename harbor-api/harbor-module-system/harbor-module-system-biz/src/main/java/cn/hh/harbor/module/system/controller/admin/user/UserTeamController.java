package cn.hh.harbor.module.system.controller.admin.user;

import cn.hh.harbor.framework.common.pojo.CommonResult;
import cn.hh.harbor.framework.security.core.util.SecurityFrameworkUtils;
import cn.hh.harbor.framework.tenant.core.context.TenantContextHolder;
import cn.hh.harbor.module.system.controller.admin.user.vo.team.UserTeamInviteRespVO;
import cn.hh.harbor.module.system.controller.admin.user.vo.team.UserTeamListRespVO;
import cn.hh.harbor.module.system.convert.user.UserConvert;
import cn.hh.harbor.module.system.dal.dataobject.invite.InviteDO;
import cn.hh.harbor.module.system.dal.dataobject.user.UserDO;
import cn.hh.harbor.module.system.service.invite.InviteService;
import cn.hh.harbor.module.system.service.permission.PermissionService;
import cn.hh.harbor.module.system.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static cn.hh.harbor.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.hh.harbor.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 管理团队")
@RestController
@RequestMapping("/system/user/team")
@Validated
@Slf4j
public class UserTeamController {

    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private InviteService inviteService;

    @GetMapping("/list")
    @Operation(summary = "获得当前租户下的管理用户")
    @PreAuthorize("@ss.hasPermission('system:userteam:list')")
    public CommonResult<List<UserTeamListRespVO>> getUserList(@Validated String nickname) {
        List<UserDO> userDOList = userService.getUserListByTenantIdOrNickname(TenantContextHolder.getTenantId(), nickname);

        List<UserTeamListRespVO> result = userDOList.stream().map(e -> {
            UserTeamListRespVO userTeamListRespVO = UserConvert.INSTANCE.convertTeam(e);
            userTeamListRespVO.setRoleIds(permissionService.getUserRoleIdListByUserId(e.getId()));
            return userTeamListRespVO;
        }).collect(Collectors.toList());

        return success(result);
    }

    @GetMapping("/query")
    @Operation(summary = "根据用户昵称进行模糊查询,并过滤已经加入当前租户的用户", description = "发送邀请的下拉框匹配")
    public CommonResult<List<UserTeamInviteRespVO>> getUserListByNickName(@Validated @NotBlank String nickname) {
        List<UserDO> userDOList = userService.getUsersByNicknameFilter(TenantContextHolder.getTenantId(), nickname);
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        List<UserTeamInviteRespVO> result = userDOList.stream().map(e -> {
            UserTeamInviteRespVO respVO = UserConvert.INSTANCE.convertTeam2(e);
            // 填充邀请状态
            InviteDO inviteDO = inviteService.selectUserInviteFilter(respVO.getId(), loginUserId);
            if (inviteDO != null){
                respVO.setInviteStatus(inviteDO.getStatus());
            }
            return respVO;
        }).collect(Collectors.toList());
        return success(result);
    }

    @DeleteMapping("/quit")
    @Operation(summary = "退出当前管理团队")
    @PreAuthorize("@ss.hasPermission('system:userteam:quit')")
    public CommonResult<Boolean> quit(@NotNull @RequestParam(value = "userId") Long userId) {
        userService.quitTeam(TenantContextHolder.getTenantId(), userId);
        return success(true);
    }
}
