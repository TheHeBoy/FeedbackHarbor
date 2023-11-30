package cn.hh.harbor.module.system.service.invite;

import cn.hh.harbor.framework.common.exception.util.ServiceExceptionUtil;
import cn.hh.harbor.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.hh.harbor.module.system.controller.admin.invite.vo.user.InviteUserReqVO;
import cn.hh.harbor.module.system.dal.dataobject.invite.InviteUserDO;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantUserDO;
import cn.hh.harbor.module.system.dal.mysql.invite.InviteUserMapper;
import cn.hh.harbor.module.system.dal.mysql.tenant.TenantUserMapper;
import cn.hh.harbor.module.system.service.tenant.TenantUserService;
import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static cn.hh.harbor.module.system.enums.ErrorCodeConstants.INVITE_USER_NO_EXISTS;
import static cn.hh.harbor.module.system.enums.invite.user.InviteStatusEnum.*;

@Service
public class InviteUserUserServiceImpl implements InviteUserService {

    @Resource
    private InviteUserMapper inviteUserMapper;

    @Resource
    private TenantUserService tenantUserService;

    @Transactional
    @Override
    public void inviteUser(InviteUserReqVO reqVO) {

        Long inviterUserId = reqVO.getInviterUserId();
        List<Long> inviteeUserIds = reqVO.getInviteeUserIds();
        List<InviteUserDO> inviteInsertList = new ArrayList<>();
        List<InviteUserDO> inviteUpdateList = new ArrayList<>();
        for (Long inviteeUserId : inviteeUserIds) {
            // 如果之前拒绝了, 再次邀请时修改状态为待回复
            InviteUserDO refuseInvite = inviteUserMapper.selectOneByInviteAndStatus(inviteeUserId, inviterUserId,
                    CollUtil.toList(REFUSE.getCode()));
            if (refuseInvite != null) {
                refuseInvite.setStatus(NO_REPLY.getCode());
                inviteUpdateList.add(refuseInvite);
            } else {
                InviteUserDO inviteUserDO = new InviteUserDO();
                inviteUserDO.setInviterUserId(reqVO.getInviterUserId());
                inviteUserDO.setInviteeUserId(inviteeUserId);
                inviteUserDO.setTenantId(reqVO.getTenantId());
                inviteUserDO.setStatus(NO_REPLY.getCode());
                inviteInsertList.add(inviteUserDO);
            }
        }

        if (CollUtil.isNotEmpty(inviteUpdateList)) {
            inviteUserMapper.updateBatch(inviteUpdateList);
        }
        if (CollUtil.isNotEmpty(inviteInsertList)) {
            inviteUserMapper.insertBatch(inviteInsertList);
        }
    }

    @Override
    public List<InviteUserDO> selectListByInviteeUserId(Long inviteeUserId) {
        return inviteUserMapper.selectList(new LambdaQueryWrapperX<InviteUserDO>()
                .eq(InviteUserDO::getInviteeUserId, inviteeUserId)
                .eq(InviteUserDO::getStatus, NO_REPLY.getCode()));
    }

    @Transactional
    @Override
    public void acceptUserInvite(Long id) {
        InviteUserDO inviteUserDO = validateExists(id);

        // 插入租户和用户关系
        tenantUserService.insert(inviteUserDO.getTenantId(), inviteUserDO.getInviteeUserId());

        // 更新邀请状态
        inviteUserMapper.updateById(new InviteUserDO().setId(id).setStatus(ACCEPT.getCode()));
    }

    @Override
    public void refuseUserInvite(Long id) {
        validateExists(id);
        // 更新邀请状态
        inviteUserMapper.updateById(new InviteUserDO().setId(id).setStatus(REFUSE.getCode()));
    }

    @Override
    public InviteUserDO selectUserInviteFilter(Long inviteeUserId, Long inviterUserId) {
        return inviteUserMapper.selectOneByInviteAndStatus(inviteeUserId, inviterUserId,
                CollUtil.toList(NO_REPLY.getCode(), REFUSE.getCode()));
    }

    private InviteUserDO validateExists(Long id) {
        InviteUserDO inviteUserDO = inviteUserMapper.selectById(id);
        if (inviteUserDO == null) {
            throw ServiceExceptionUtil.exception(INVITE_USER_NO_EXISTS);
        }
        return inviteUserDO;
    }

}
