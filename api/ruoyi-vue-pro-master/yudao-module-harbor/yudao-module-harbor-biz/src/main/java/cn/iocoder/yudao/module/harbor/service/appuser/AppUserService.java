package cn.iocoder.yudao.module.harbor.service.appuser;

import javax.validation.*;

import cn.iocoder.yudao.module.harbor.controller.app.appuser.vo.AppUserCreateReqVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;

/**
 * App用户 Service 接口
 * <p>
 * hehong
 */
public interface AppUserService {

    /**
     * 创建App用户
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAppUser(@Valid AppUserCreateReqVO createReqVO);

    /**
     * 删除App用户
     *
     * @param id 编号
     */
    void deleteAppUser(Long id);

    AppUserDO getAppUserByUserName(String userName);

    AppUserDO getByOpenIdAndType(String openId, Integer userType);

    int deleteByOpenIdAndType(String openId, Integer userType);

    /**
     * 获取用户
     *
     * @param loginUserId 登录用户id
     * @return {@link AppUserDO}
     */
    AppUserDO getUser(Long loginUserId);
}
