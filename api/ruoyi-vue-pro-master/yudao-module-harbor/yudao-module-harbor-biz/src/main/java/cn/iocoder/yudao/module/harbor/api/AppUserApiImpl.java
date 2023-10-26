package cn.iocoder.yudao.module.harbor.api;

import cn.iocoder.yudao.module.harbor.api.appuser.AppUserApi;
import cn.iocoder.yudao.module.harbor.api.appuser.dto.AppUserRespDTO;
import cn.iocoder.yudao.module.harbor.controller.app.appuser.vo.AppUserCreateReqVO;
import cn.iocoder.yudao.module.harbor.convert.appuser.AppUserConvert;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;
import cn.iocoder.yudao.module.harbor.service.appuser.AppUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.enums.UserTypeEnum.ADMIN;

@Service
public class AppUserApiImpl implements AppUserApi {

    @Resource
    AppUserService appUserService;


    @Override
    public AppUserRespDTO getAppUser(Long adminId) {
        AppUserDO appUserDO = appUserService.getByOpenIdAndType(String.valueOf(adminId), ADMIN.getValue());
        return AppUserConvert.INSTANCE.convertApi(appUserDO);
    }

    @Override
    public AppUserRespDTO createAppUser(Long adminId, String nickname, String avatar) {
        AppUserCreateReqVO createReqVO = new AppUserCreateReqVO();
        createReqVO.setUserType(ADMIN.getValue())
                .setNickname(nickname)
                .setAvatar(avatar)
                .setUserOpenId(String.valueOf(adminId));
        appUserService.createAppUser(createReqVO);

        return AppUserConvert.INSTANCE.convertApi(createReqVO);
    }

    @Override
    public void deleteAppUser(Long adminId) {
        appUserService.deleteByOpenIdAndType(String.valueOf(adminId), ADMIN.getValue());
    }

}
