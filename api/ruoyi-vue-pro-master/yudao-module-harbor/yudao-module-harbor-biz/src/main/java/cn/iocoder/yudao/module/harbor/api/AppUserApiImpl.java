package cn.iocoder.yudao.module.harbor.api;

import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.module.harbor.api.appuser.AppUserApi;
import cn.iocoder.yudao.module.harbor.api.appuser.dto.AppUserRespDTO;
import cn.iocoder.yudao.module.harbor.convert.appuser.AppUserConvert;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AdminAppUserDO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;
import cn.iocoder.yudao.module.harbor.dal.mysql.appuser.AdminAppUserMapper;
import cn.iocoder.yudao.module.harbor.dal.mysql.appuser.AppUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class AppUserApiImpl implements AppUserApi {
    @Resource
    AdminAppUserMapper adminAppUserMapper;

    @Resource
    AppUserMapper appUserMapper;


    @Override
    public AppUserRespDTO getAppUser(Long adminId) {
        AdminAppUserDO adminAppUserDO = adminAppUserMapper.selectOne(AdminAppUserDO::getAdminId, adminId);
        AppUserDO appUserDO = appUserMapper.selectById(adminAppUserDO.getAppId());
        return AppUserConvert.INSTANCE.convertApi(appUserDO);
    }

    @Transactional
    @Override
    public AppUserRespDTO createAppUser(Long adminId, String nickname, String avatar) {
        AppUserDO appUserDO = AppUserDO.builder()
                .userType(UserTypeEnum.ADMIN.getValue())
                .nickname(nickname)
                .avatar(avatar)
                .build();
        appUserMapper.insert(appUserDO);

        // 插入关联关系
        adminAppUserMapper.insert(AdminAppUserDO.builder().adminId(adminId).appId(appUserDO.getId()).build());

        return AppUserConvert.INSTANCE.convertApi(appUserDO);
    }
}
