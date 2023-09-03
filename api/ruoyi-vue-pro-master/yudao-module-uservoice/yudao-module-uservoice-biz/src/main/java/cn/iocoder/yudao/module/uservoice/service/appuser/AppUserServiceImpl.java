package cn.iocoder.yudao.module.uservoice.service.appuser;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.uservoice.controller.admin.appuser.vo.*;
import cn.iocoder.yudao.module.uservoice.dal.dataobject.appuser.AppUserDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.uservoice.convert.appuser.AppUserConvert;
import cn.iocoder.yudao.module.uservoice.dal.mysql.appuser.AppUserMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.uservoice.enums.ErrorCodeConstants.*;

/**
 * App用户 Service 实现类
 * <p>
 * hehong
 */
@Service
@Validated
public class AppUserServiceImpl implements AppUserService {

    @Resource
    private AppUserMapper appUserMapper;

    @Override
    public Long createAppUser(AppUserCreateReqVO createReqVO) {
        // 插入
        AppUserDO appUser = AppUserConvert.INSTANCE.convert(createReqVO);
        appUserMapper.insert(appUser);
        // 返回
        return appUser.getId();
    }

    @Override
    public void updateAppUser(AppUserUpdateReqVO updateReqVO) {
        // 校验存在
        validateAppUserExists(updateReqVO.getId());
        // 更新
        AppUserDO updateObj = AppUserConvert.INSTANCE.convert(updateReqVO);
        appUserMapper.updateById(updateObj);
    }

    @Override
    public void deleteAppUser(Long id) {
        // 校验存在
        validateAppUserExists(id);
        // 删除
        appUserMapper.deleteById(id);
    }

    private void validateAppUserExists(Long id) {
        if (appUserMapper.selectById(id) == null) {
            throw exception(APP_USER_NOT_EXISTS);
        }
    }

    @Override
    public AppUserDO getAppUser(Long id) {
        return appUserMapper.selectById(id);
    }

    @Override
    public List<AppUserDO> getAppUserList(Collection<Long> ids) {
        return appUserMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<AppUserDO> getAppUserPage(AppUserPageReqVO pageReqVO) {
        return appUserMapper.selectPage(pageReqVO);
    }

    @Override
    public List<AppUserDO> getAppUserList(AppUserExportReqVO exportReqVO) {
        return appUserMapper.selectList(exportReqVO);
    }

    @Override
    public AppUserDO getAppUserByUserName(String userName) {
        return appUserMapper.getAppUserByUserName(userName);
    }

    @Override
    public AppUserDO getUser(Long loginUserId) {
        return appUserMapper.selectById(loginUserId);
    }

}
