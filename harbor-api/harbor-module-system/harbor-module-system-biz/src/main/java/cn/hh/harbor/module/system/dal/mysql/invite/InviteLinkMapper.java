package cn.hh.harbor.module.system.dal.mysql.invite;

import cn.hh.harbor.framework.mybatis.core.mapper.BaseMapperX;
import cn.hh.harbor.module.system.dal.dataobject.invite.InviteLinkDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InviteLinkMapper extends BaseMapperX<InviteLinkDO> {

    default InviteLinkDO selectByTenantId(Long tenantId) {
        return selectOne(InviteLinkDO::getTenantId, tenantId);
    }

    default InviteLinkDO selectByCode(String code) {
        return selectOne(InviteLinkDO::getCode, code);
    }
}
