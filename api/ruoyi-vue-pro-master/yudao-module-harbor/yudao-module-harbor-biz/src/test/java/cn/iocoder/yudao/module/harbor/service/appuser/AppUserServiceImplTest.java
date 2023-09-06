package cn.iocoder.yudao.module.harbor.service.appuser;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.harbor.controller.admin.appuser.vo.*;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;
import cn.iocoder.yudao.module.harbor.dal.mysql.appuser.AppUserMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.springframework.context.annotation.Import;
import java.util.*;

import static cn.iocoder.yudao.module.harbor.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link AppUserServiceImpl} 的单元测试类
 *
 *  hehong
 */
@Import(AppUserServiceImpl.class)
public class AppUserServiceImplTest extends BaseDbUnitTest {

    @Resource
    private AppUserServiceImpl appUserService;

    @Resource
    private AppUserMapper appUserMapper;

    @Test
    public void testCreateAppUser_success() {
        // 准备参数
        AppUserCreateReqVO reqVO = randomPojo(AppUserCreateReqVO.class);

        // 调用
        Long appUserId = appUserService.createAppUser(reqVO);
        // 断言
        assertNotNull(appUserId);
        // 校验记录的属性是否正确
        AppUserDO appUser = appUserMapper.selectById(appUserId);
        assertPojoEquals(reqVO, appUser);
    }

    @Test
    public void testUpdateAppUser_success() {
        // mock 数据
        AppUserDO dbAppUser = randomPojo(AppUserDO.class);
        appUserMapper.insert(dbAppUser);// @Sql: 先插入出一条存在的数据
        // 准备参数
        AppUserUpdateReqVO reqVO = randomPojo(AppUserUpdateReqVO.class, o -> {
            o.setId(dbAppUser.getId()); // 设置更新的 ID
        });

        // 调用
        appUserService.updateAppUser(reqVO);
        // 校验是否更新正确
        AppUserDO appUser = appUserMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, appUser);
    }

    @Test
    public void testUpdateAppUser_notExists() {
        // 准备参数
        AppUserUpdateReqVO reqVO = randomPojo(AppUserUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> appUserService.updateAppUser(reqVO), APP_USER_NOT_EXISTS);
    }

    @Test
    public void testDeleteAppUser_success() {
        // mock 数据
        AppUserDO dbAppUser = randomPojo(AppUserDO.class);
        appUserMapper.insert(dbAppUser);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbAppUser.getId();

        // 调用
        appUserService.deleteAppUser(id);
       // 校验数据不存在了
       assertNull(appUserMapper.selectById(id));
    }

    @Test
    public void testDeleteAppUser_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> appUserService.deleteAppUser(id), APP_USER_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetAppUserPage() {
       // mock 数据
       AppUserDO dbAppUser = randomPojo(AppUserDO.class, o -> { // 等会查询到
           o.setCreateTime(null);
           o.setUserType(null);
           o.setUsername(null);
           o.setPassword(null);
           o.setUserOpenId(null);
           o.setAvatar(null);
           o.setNickname(null);
           o.setStatus(null);
       });
       appUserMapper.insert(dbAppUser);
       // 测试 createTime 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setCreateTime(null)));
       // 测试 userType 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setUserType(null)));
       // 测试 username 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setUsername(null)));
       // 测试 password 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setPassword(null)));
       // 测试 userOpenId 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setUserOpenId(null)));
       // 测试 avatar 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setAvatar(null)));
       // 测试 nickname 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setNickname(null)));
       // 测试 status 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setStatus(null)));
       // 准备参数
       AppUserPageReqVO reqVO = new AppUserPageReqVO();
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setUserType(null);
       reqVO.setUsername(null);
       reqVO.setPassword(null);
       reqVO.setUserOpenId(null);
       reqVO.setAvatar(null);
       reqVO.setNickname(null);
       reqVO.setStatus(null);

       // 调用
       PageResult<AppUserDO> pageResult = appUserService.getAppUserPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbAppUser, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetAppUserList() {
       // mock 数据
       AppUserDO dbAppUser = randomPojo(AppUserDO.class, o -> { // 等会查询到
           o.setCreateTime(null);
           o.setUserType(null);
           o.setUsername(null);
           o.setPassword(null);
           o.setUserOpenId(null);
           o.setAvatar(null);
           o.setNickname(null);
           o.setStatus(null);
       });
       appUserMapper.insert(dbAppUser);
       // 测试 createTime 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setCreateTime(null)));
       // 测试 userType 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setUserType(null)));
       // 测试 username 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setUsername(null)));
       // 测试 password 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setPassword(null)));
       // 测试 userOpenId 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setUserOpenId(null)));
       // 测试 avatar 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setAvatar(null)));
       // 测试 nickname 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setNickname(null)));
       // 测试 status 不匹配
       appUserMapper.insert(cloneIgnoreId(dbAppUser, o -> o.setStatus(null)));
       // 准备参数
       AppUserExportReqVO reqVO = new AppUserExportReqVO();
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setUserType(null);
       reqVO.setUsername(null);
       reqVO.setPassword(null);
       reqVO.setUserOpenId(null);
       reqVO.setAvatar(null);
       reqVO.setNickname(null);
       reqVO.setStatus(null);

       // 调用
       List<AppUserDO> list = appUserService.getAppUserList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbAppUser, list.get(0));
    }

}
