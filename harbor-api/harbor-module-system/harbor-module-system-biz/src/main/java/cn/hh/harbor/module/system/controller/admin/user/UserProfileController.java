package cn.hh.harbor.module.system.controller.admin.user;

import cn.hh.harbor.framework.common.pojo.CommonResult;
import cn.hh.harbor.framework.datapermission.core.annotation.DataPermission;
import cn.hh.harbor.module.system.controller.admin.user.vo.profile.UserProfileRespVO;
import cn.hh.harbor.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import cn.hh.harbor.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import cn.hh.harbor.module.system.convert.user.UserConvert;
import cn.hh.harbor.module.system.dal.dataobject.permission.RoleDO;
import cn.hh.harbor.module.system.dal.dataobject.user.UserDO;
import cn.hh.harbor.module.system.service.permission.PermissionService;
import cn.hh.harbor.module.system.service.permission.RoleService;
import cn.hh.harbor.module.system.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.hh.harbor.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.hh.harbor.framework.common.pojo.CommonResult.success;
import static cn.hh.harbor.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.hh.harbor.module.infra.enums.ErrorCodeConstants.FILE_IS_EMPTY;

@Tag(name = "管理后台 - 用户个人中心")
@RestController
@RequestMapping("/system/user/profile")
@Validated
@Slf4j
public class UserProfileController {

    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;

    @GetMapping("/get")
    @Operation(summary = "获得登录用户信息")
    @DataPermission(enable = false)
    public CommonResult<UserProfileRespVO> profile() {
        // 获得用户基本信息
        UserDO user = userService.getUser(getLoginUserId());
        UserProfileRespVO resp = UserConvert.INSTANCE.convert03(user);
        // 获得用户角色
        List<RoleDO> userRoles = roleService.getRoleListFromCache(permissionService.getUserRoleIdListByUserId(user.getId()));
        resp.setRoles(UserConvert.INSTANCE.convertList(userRoles));
        return success(resp);
    }

    @PutMapping("/update")
    @Operation(summary = "修改用户个人信息")
    public CommonResult<Boolean> updateUserProfile(@Valid @RequestBody UserProfileUpdateReqVO reqVO) {
        userService.updateUserProfile(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "修改用户个人密码")
    public CommonResult<Boolean> updateUserProfilePassword(@Valid @RequestBody UserProfileUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
        return success(true);
    }

    @RequestMapping(value = "/update-avatar", method = {RequestMethod.POST, RequestMethod.PUT}) // 解决 uni-app 不支持 Put 上传文件的问题
    @Operation(summary = "上传用户个人头像")
    public CommonResult<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw exception(FILE_IS_EMPTY);
        }
        String avatar = userService.updateUserAvatar(getLoginUserId(), file.getInputStream());
        return success(avatar);
    }

}
