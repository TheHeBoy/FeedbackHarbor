package cn.iocoder.yudao.module.system.api.user;

import cn.iocoder.yudao.module.system.api.user.dto.UserRespDTO;
import cn.iocoder.yudao.module.system.convert.user.UserConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.user.UserDO;
import cn.iocoder.yudao.module.system.service.user.UserService;
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
        return UserConvert.INSTANCE.convert4(user);
    }
}
