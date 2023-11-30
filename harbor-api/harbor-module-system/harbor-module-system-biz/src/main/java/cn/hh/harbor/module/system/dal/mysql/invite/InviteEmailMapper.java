package cn.hh.harbor.module.system.dal.mysql.invite;

import cn.hh.harbor.framework.mybatis.core.mapper.BaseMapperX;
import cn.hh.harbor.module.system.dal.dataobject.invite.InviteEmailDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InviteEmailMapper extends BaseMapperX<InviteEmailDO> {
}
