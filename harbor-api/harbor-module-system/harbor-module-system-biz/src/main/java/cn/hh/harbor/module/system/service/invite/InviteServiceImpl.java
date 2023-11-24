package cn.hh.harbor.module.system.service.invite;

import cn.hh.harbor.framework.common.exception.util.ServiceExceptionUtil;
import cn.hh.harbor.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.hh.harbor.framework.tenant.core.util.TenantUtils;
import cn.hh.harbor.module.system.controller.admin.invite.vo.InviteUserReqVO;
import cn.hh.harbor.module.system.convert.invite.InviteConvert;
import cn.hh.harbor.module.system.dal.dataobject.invite.InviteDO;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantUserDO;
import cn.hh.harbor.module.system.dal.mysql.invite.InviteMapper;
import cn.hh.harbor.module.system.dal.mysql.tenant.TenantUserMapper;
import cn.hh.harbor.module.system.enums.invite.InviteStatusEnum;
import cn.hh.harbor.module.system.enums.invite.InviteTypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static cn.hh.harbor.module.system.enums.ErrorCodeConstants.INVITE_NO_EXISTS;

@Service
public class InviteServiceImpl implements InviteService {

    @Resource
    private InviteMapper inviteMapper;

    @Resource
    private TenantUserMapper tenantUserMapper;


    @Override
    public void inviteUser(InviteUserReqVO reqVO, Long tenantId) {
        List<Long> inviteeUserIds = reqVO.getInviteeUserIds();
        List<InviteDO> inviteInsert = new ArrayList<>();
        for (Long inviteeUserId : inviteeUserIds) {
            InviteDO inviteDO = new InviteDO();
            inviteDO.setInviterUserId(reqVO.getInviterUserId());
            inviteDO.setInviteeUserId(inviteeUserId);
            inviteDO.setType(InviteTypeEnum.USER.getCode());
            inviteDO.setStatus(InviteStatusEnum.NO_REPLY.getCode());
            inviteDO.setTenantId(tenantId);
            inviteInsert.add(inviteDO);
        }

        inviteMapper.insertBatch(inviteInsert);
    }

    @Override
    public List<InviteDO> selectListByInviteeUserId(Long inviteeUserId) {
        return inviteMapper.selectList(new LambdaQueryWrapperX<InviteDO>()
                .eq(InviteDO::getInviteeUserId, inviteeUserId)
                .eq(InviteDO::getType, InviteTypeEnum.USER.getCode())
                .eq(InviteDO::getStatus, InviteStatusEnum.NO_REPLY.getCode()));
    }

    @Transactional
    @Override
    public void acceptUserInvite(Long id) {
        InviteDO inviteDO = validateExists(id);

        // 插入租户和用户关系
        TenantUserDO tenantUserDO = new TenantUserDO();
        tenantUserDO.setUserId(inviteDO.getInviteeUserId());
        tenantUserDO.setTenantId(inviteDO.getTenantId());
        tenantUserMapper.insert(tenantUserDO);

        // 更新邀请状态
        inviteMapper.updateById(new InviteDO().setId(id).setStatus(InviteStatusEnum.ACCEPT.getCode()));
    }

    @Override
    public void refuseUserInvite(Long id) {
        validateExists(id);
        // 更新邀请状态
        inviteMapper.updateById(new InviteDO().setId(id).setStatus(InviteStatusEnum.REFUSE.getCode()));
    }


    private InviteDO validateExists(Long id) {
        InviteDO inviteDO = inviteMapper.selectById(id);
        if (inviteDO == null) {
            throw ServiceExceptionUtil.exception(INVITE_NO_EXISTS);
        }
        return inviteDO;
    }

}
