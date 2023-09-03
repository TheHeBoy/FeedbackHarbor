package cn.iocoder.yudao.module.uservoice.dal.mysql.appuser;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.uservoice.dal.dataobject.appuser.AppUserDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.uservoice.controller.admin.appuser.vo.*;

/**
 * App用户 Mapper
 * <p>
 * hehong
 */
@Mapper
public interface AppUserMapper extends BaseMapperX<AppUserDO> {

    default PageResult<AppUserDO> selectPage(AppUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AppUserDO>()
                .betweenIfPresent(AppUserDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(AppUserDO::getUserType, reqVO.getUserType())
                .likeIfPresent(AppUserDO::getUsername, reqVO.getUsername())
                .eqIfPresent(AppUserDO::getPassword, reqVO.getPassword())
                .eqIfPresent(AppUserDO::getUserOpenId, reqVO.getUserOpenId())
                .eqIfPresent(AppUserDO::getAvatar, reqVO.getAvatar())
                .likeIfPresent(AppUserDO::getNickname, reqVO.getNickname())
                .eqIfPresent(AppUserDO::getStatus, reqVO.getStatus())
                .orderByDesc(AppUserDO::getId));
    }

    default List<AppUserDO> selectList(AppUserExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<AppUserDO>()
                .betweenIfPresent(AppUserDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(AppUserDO::getUserType, reqVO.getUserType())
                .likeIfPresent(AppUserDO::getUsername, reqVO.getUsername())
                .eqIfPresent(AppUserDO::getPassword, reqVO.getPassword())
                .eqIfPresent(AppUserDO::getUserOpenId, reqVO.getUserOpenId())
                .eqIfPresent(AppUserDO::getAvatar, reqVO.getAvatar())
                .likeIfPresent(AppUserDO::getNickname, reqVO.getNickname())
                .eqIfPresent(AppUserDO::getStatus, reqVO.getStatus())
                .orderByDesc(AppUserDO::getId));
    }

    default AppUserDO getAppUserByUserName(String userName) {
        return selectOne(AppUserDO::getUserType, userName);
    }
}
