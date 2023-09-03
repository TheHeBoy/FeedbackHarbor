package cn.iocoder.yudao.module.uservoice.service.appuser;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.uservoice.controller.admin.appuser.vo.*;
import cn.iocoder.yudao.module.uservoice.dal.dataobject.appuser.AppUserDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * App用户 Service 接口
 *
 *  hehong
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
     * 更新App用户
     *
     * @param updateReqVO 更新信息
     */
    void updateAppUser(@Valid AppUserUpdateReqVO updateReqVO);

    /**
     * 删除App用户
     *
     * @param id 编号
     */
    void deleteAppUser(Long id);

    /**
     * 获得App用户
     *
     * @param id 编号
     * @return App用户
     */
    AppUserDO getAppUser(Long id);

    /**
     * 获得App用户列表
     *
     * @param ids 编号
     * @return App用户列表
     */
    List<AppUserDO> getAppUserList(Collection<Long> ids);

    /**
     * 获得App用户分页
     *
     * @param pageReqVO 分页查询
     * @return App用户分页
     */
    PageResult<AppUserDO> getAppUserPage(AppUserPageReqVO pageReqVO);

    /**
     * 获得App用户列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return App用户列表
     */
    List<AppUserDO> getAppUserList(AppUserExportReqVO exportReqVO);

    /**
     * 通过用户名得到用户信息
     *
     * @return 用户信息
     */
    AppUserDO getAppUserByUserName(String userName);

    /**
     * 获取用户
     *
     * @param loginUserId 登录用户id
     * @return {@link AppUserDO}
     */
    AppUserDO getUser(Long loginUserId);
}
