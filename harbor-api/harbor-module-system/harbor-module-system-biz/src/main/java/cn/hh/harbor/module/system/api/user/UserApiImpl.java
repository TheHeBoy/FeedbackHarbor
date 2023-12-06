package cn.hh.harbor.module.system.api.user;

import cn.hh.harbor.framework.security.core.LoginUser;
import cn.hh.harbor.framework.security.core.util.SecurityFrameworkUtils;
import cn.hh.harbor.module.system.api.user.dto.UserRespDTO;
import cn.hh.harbor.module.system.convert.user.UserConvert;
import cn.hh.harbor.module.system.dal.dataobject.user.UserDO;
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

    @Override
    public UserRespDTO getUser(Long id) {
        UserDO user = userService.getUser(id);
        UserRespDTO userRespDTO = UserConvert.INSTANCE.convert4(user);
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if (loginUser != null) {
            userRespDTO.setUserType(loginUser.getUserType());
        }
        return userRespDTO;
    }
}
