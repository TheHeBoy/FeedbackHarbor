package cn.hh.harbor.module.system.api.user;

import cn.hh.harbor.framework.common.enums.UserTypeEnum;
import cn.hh.harbor.framework.security.core.LoginUser;
import cn.hh.harbor.framework.security.core.util.SecurityFrameworkUtils;
import cn.hh.harbor.framework.tenant.core.context.TenantContextHolder;
import cn.hh.harbor.module.system.api.user.dto.UserRespDTO;
import cn.hh.harbor.module.system.convert.user.UserConvert;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantUserDO;
import cn.hh.harbor.module.system.dal.dataobject.user.UserDO;
import cn.hh.harbor.module.system.service.tenant.TenantUserService;
import cn.hh.harbor.module.system.service.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 用户 API 实现类
 */
@Service
public class UserApiImpl implements UserApi {

    @Resource
    private UserService userService;

    @Resource
    private TenantUserService tenantUserService;

    @Override
    public UserRespDTO getUser(Long id) {
        UserDO user = userService.getUser(id);
        UserRespDTO userRespDTO = UserConvert.INSTANCE.convert4(user);
        
        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId != null) {
            // 如果当前用户在当前租户下绑定了关系，就视为管理员
            TenantUserDO tenantUserDO = tenantUserService.get(tenantId, user.getId());
            if (tenantUserDO != null) {
                userRespDTO.setUserType(UserTypeEnum.ADMIN.getValue());
            }
        }
        return userRespDTO;
    }
}
