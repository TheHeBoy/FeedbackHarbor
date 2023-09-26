package cn.iocoder.yudao.module.harbor.dal.mysql.appuser;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.harbor.dal.dataobject.appuser.AppUserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * App用户 Mapper
 * <p>
 * hehong
 */
@Mapper
public interface AppUserMapper extends BaseMapperX<AppUserDO> {

    default AppUserDO getAppUserByUserName(String userName) {
        return selectOne(AppUserDO::getUserType, userName);
    }

    default AppUserDO getAppUserByUserOpenId(String openId){
        return selectOne(AppUserDO::getUserOpenId, openId);

    }
}
