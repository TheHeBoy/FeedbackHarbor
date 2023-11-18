package cn.iocoder.yudao.module.system.api.user;

import cn.iocoder.yudao.module.system.api.user.dto.UserRespDTO;

import java.util.Collection;
import java.util.List;

/**
 * 用户 API 接口
 */
public interface UserApi {

    /**
     * 通过用户 ID 查询用户
     *
     * @param id 用户ID
     * @return 用户对象信息
     */
    UserRespDTO getUser(Long id);
}
