package cn.iocoder.yudao.module.harbor.service.appuser;

import cn.iocoder.yudao.module.harbor.controller.app.appuser.vo.AppUserCreateReqVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;

import cn.iocoder.yudao.module.harbor.convert.appuser.AppUserConvert;
import cn.iocoder.yudao.module.harbor.dal.mysql.appuser.AppUserMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.harbor.enums.ErrorCodeConstants.*;

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
    public AppUserDO getAppUserByUserName(String userName) {
        return appUserMapper.getAppUserByUserName(userName);
    }

    @Override
    public AppUserDO getAppUserByUserOpenId(String openId) {
        return appUserMapper.getAppUserByUserOpenId(openId);
    }

    @Override
    public AppUserDO getUser(Long loginUserId) {
        return appUserMapper.selectById(loginUserId);
    }

}
