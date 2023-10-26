package cn.iocoder.yudao.module.harbor.api.appuser;


import cn.iocoder.yudao.module.harbor.api.appuser.dto.AppUserRespDTO;

/**
 * App 用户 API 接口
 */
public interface AppUserApi {

    /**
     * 通过 Admin ID 查询 App
     *
     * @param adminId Admin ID
     * @return 用户对象信息
     */
    AppUserRespDTO getAppUser(Long adminId);

    /**
     * 通过 Admin ID 生成 App
     *
     * @param adminId  Admin ID
     * @param nickname 用户昵称
     * @param avatar 用户头像
     * @return 用户对象信息
     */
    AppUserRespDTO createAppUser(Long adminId, String nickname, String avatar);
}