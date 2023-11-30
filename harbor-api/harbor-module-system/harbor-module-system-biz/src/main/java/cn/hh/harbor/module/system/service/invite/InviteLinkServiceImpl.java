package cn.hh.harbor.module.system.service.invite;

import cn.hh.harbor.framework.common.enums.CommonStatusEnum;
import cn.hh.harbor.framework.common.exception.util.ServiceExceptionUtil;
import cn.hh.harbor.framework.common.util.date.LocalDateTimeUtils;
import cn.hh.harbor.module.system.dal.dataobject.invite.InviteLinkDO;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantUserDO;
import cn.hh.harbor.module.system.dal.mysql.invite.InviteLinkMapper;
import cn.hh.harbor.module.system.dal.mysql.tenant.TenantUserMapper;
import cn.hh.harbor.module.system.enums.ErrorCodeConstants;
import cn.hh.harbor.module.system.service.tenant.TenantUserService;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class InviteLinkServiceImpl implements InviteLinkService {


    @Resource
    private InviteLinkMapper inviteLinkMapper;

    @Resource
    private TenantUserService tenantUserService;

    @Override
    public String create(Long tenantId) {
        InviteLinkDO inviteLinkDO = inviteLinkMapper.selectByTenantId(tenantId);
        if (inviteLinkDO != null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.INVITE_LINK_EXISTS);
        }

        inviteLinkDO = new InviteLinkDO()
                .setCode(IdUtil.fastUUID())
                .setStatus(CommonStatusEnum.ENABLE.getStatus())
                .setExpireTime(LocalDateTimeUtils.MAX)
                .setTenantId(tenantId);

        inviteLinkMapper.insert(inviteLinkDO);
        return inviteLinkDO.getCode();
    }

    @Override
    public String get(Long tenantId) {
        InviteLinkDO inviteLinkDO = inviteLinkMapper.selectByTenantId(tenantId);
        return Optional.ofNullable(inviteLinkDO).map(InviteLinkDO::getCode).orElse(null);
    }

    @Override
    public void delete(String code) {
        InviteLinkDO inviteLinkDO = validateExist(code);
        inviteLinkMapper.deleteById(new InviteLinkDO().setId(inviteLinkDO.getId()).setCode(code));
    }

    @Override
    public void joinByCode(String code, Long userId) {
        InviteLinkDO inviteLinkDO = validateExist(code);
        Long tenantId = inviteLinkDO.getTenantId();
        tenantUserService.insert(tenantId, userId);
    }


    private InviteLinkDO validateExist(String code) {
        InviteLinkDO inviteLinkDO = inviteLinkMapper.selectByCode(code);
        if (inviteLinkDO == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.INVITE_LINK_NO_EXISTS);
        }
        return inviteLinkDO;
    }
}
