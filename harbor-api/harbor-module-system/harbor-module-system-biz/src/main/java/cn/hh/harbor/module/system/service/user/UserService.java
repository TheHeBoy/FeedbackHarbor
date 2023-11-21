package cn.hh.harbor.module.system.service.user;

import cn.hutool.core.collection.CollUtil;
import cn.hh.harbor.framework.common.enums.UserTypeEnum;
import cn.hh.harbor.framework.common.util.collection.CollectionUtils;
import cn.hh.harbor.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import cn.hh.harbor.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import cn.hh.harbor.module.system.controller.admin.user.vo.user.*;
import cn.hh.harbor.framework.common.pojo.PageResult;
import cn.hh.harbor.module.system.dal.dataobject.user.UserDO;
import cn.hh.harbor.module.system.service.user.vo.UserCreateSocialReqVO;

import javax.validation.Valid;
import java.io.InputStream;
import java.util.*;

/**
 * 用户 Service 接口
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param reqVO 用户信息
     * @return 用户编号
     */
    Long createUser(@Valid UserCreateReqVO reqVO, UserTypeEnum userTypeEnum);


    /**
     * 创建社交用户
     *
     * @param reqVO 用户信息
     * @return 用户编号
     */
    Long createSocialUser(UserCreateSocialReqVO reqVO, UserTypeEnum userTypeEnum);

    /**
     * 修改用户
     *
     * @param reqVO 用户信息
     */
    void updateUser(@Valid UserUpdateReqVO reqVO);

    /**
     * 更新用户的最后登陆信息
     *
     * @param id      用户编号
     * @param loginIp 登陆 IP
     */
    void updateUserLogin(Long id, String loginIp);

    /**
     * 修改用户个人信息
     *
     * @param id    用户编号
     * @param reqVO 用户个人信息
     */
    void updateUserProfile(Long id, @Valid UserProfileUpdateReqVO reqVO);

    /**
     * 修改用户个人密码
     *
     * @param id    用户编号
     * @param reqVO 更新用户个人密码
     */
    void updateUserPassword(Long id, @Valid UserProfileUpdatePasswordReqVO reqVO);

    /**
     * 更新用户头像
     *
     * @param id         用户 id
     * @param avatarFile 头像文件
     */
    String updateUserAvatar(Long id, InputStream avatarFile) throws Exception;

    /**
     * 修改密码
     *
     * @param id       用户编号
     * @param password 密码
     */
    void updateUserPassword(Long id, String password);

    /**
     * 修改状态
     *
     * @param id     用户编号
     * @param status 状态
     */
    void updateUserStatus(Long id, Integer status);

    /**
     * 删除用户
     *
     * @param id 用户编号
     */
    void deleteUser(Long id);

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象信息
     */
    UserDO getUserByUsername(String username);

    /**
     * 通过手机号获取用户
     *
     * @param mobile 手机号
     * @return 用户对象信息
     */
    UserDO getUserByMobile(String mobile);

    /**
     * 获得用户分页列表
     *
     * @param reqVO 分页条件
     * @return 分页列表
     */
    PageResult<UserDO> getUserPage(UserPageReqVO reqVO);

    /**
     * 通过用户 ID 查询用户
     *
     * @param id 用户ID
     * @return 用户对象信息
     */
    UserDO getUser(Long id);

    /**
     * 获得用户列表
     *
     * @param ids 用户编号数组
     * @return 用户列表
     */
    List<UserDO> getUserList(Collection<Long> ids);

    /**
     * 校验用户们是否有效。如下情况，视为无效：
     * 1. 用户编号不存在
     * 2. 用户被禁用
     *
     * @param ids 用户编号数组
     */
    void validateUserList(Collection<Long> ids);

    /**
     * 获得用户 Map
     *
     * @param ids 用户编号数组
     * @return 用户 Map
     */
    default Map<Long, UserDO> getUserMap(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return new HashMap<>();
        }
        return CollectionUtils.convertMap(getUserList(ids), UserDO::getId);
    }

    /**
     * 获得用户列表
     *
     * @param reqVO 列表请求
     * @return 用户列表
     */
    List<UserDO> getUserList(UserExportReqVO reqVO);

    /**
     * 获得用户列表，基于昵称模糊匹配
     *
     * @param nickname 昵称
     * @return 用户列表
     */
    List<UserDO> getUserListByNickname(String nickname);

    /**
     * 批量导入用户
     *
     * @param importUsers     导入用户列表
     * @param isUpdateSupport 是否支持更新
     * @return 导入结果
     */
    UserImportRespVO importUserList(List<UserImportExcelVO> importUsers, boolean isUpdateSupport);

    /**
     * 获得指定状态的用户们
     *
     * @param status 状态
     * @return 用户们
     */
    List<UserDO> getUserListByStatus(Integer status);

    /**
     * 判断密码是否匹配
     *
     * @param rawPassword     未加密的密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    boolean isPasswordMatch(String rawPassword, String encodedPassword);

    /**
     * 通过社交信息得到用户
     *
     * @param openId     社交 openid
     * @param socialType 社交平台的类型
     * @return {@link UserDO}
     */
    UserDO getUserByOpenIdAndSocialType(String openId, Integer socialType);
}
