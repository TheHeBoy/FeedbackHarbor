package cn.hh.harbor.module.system.dal.mysql.invite;

import cn.hh.harbor.framework.mybatis.core.mapper.BaseMapperX;
import cn.hh.harbor.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.hh.harbor.module.system.dal.dataobject.invite.InviteDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InviteMapper extends BaseMapperX<InviteDO> {

}
