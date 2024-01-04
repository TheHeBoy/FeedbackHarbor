package cn.hh.harbor.module.system.service.invite;

import cn.hh.harbor.framework.common.enums.CommonStatusEnum;
import cn.hh.harbor.framework.common.exception.util.ServiceExceptionUtil;
import cn.hh.harbor.framework.common.util.date.DateUtils;
import cn.hh.harbor.framework.common.util.date.LocalDateTimeUtils;
import cn.hh.harbor.module.system.controller.admin.invite.vo.link.InviteSendMailReqVO;
import cn.hh.harbor.module.system.dal.dataobject.invite.InviteLinkDO;
import cn.hh.harbor.module.system.dal.dataobject.tenant.TenantDO;
import cn.hh.harbor.module.system.dal.mysql.invite.InviteLinkMapper;
import cn.hh.harbor.module.system.enums.ErrorCodeConstants;
import cn.hh.harbor.module.system.enums.invite.link.InviteLinkTypeEnum;
import cn.hh.harbor.module.system.enums.mail.MailTemplateEnum;
import cn.hh.harbor.module.system.service.mail.MailSendService;
import cn.hh.harbor.module.system.service.tenant.TenantService;
import cn.hh.harbor.module.system.service.tenant.TenantUserService;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Service
public class InviteLinkServiceImpl implements InviteLinkService {


    @Resource
    private InviteLinkMapper inviteLinkMapper;

    @Resource
    private TenantUserService tenantUserService;

    @Resource
    private TenantService tenantService;

    @Resource
    private MailSendService mailSendService;

    @Value("${harbor.mail-link-expire-time}")
    private Duration mailLinkExpireTime;


    @Override
    public String createLink(Long tenantId) {
        InviteLinkDO inviteLinkDO = inviteLinkMapper.selectByTenantIdAndLink(tenantId);
        if (inviteLinkDO != null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.INVITE_LINK_EXISTS);
        }

        inviteLinkDO = new InviteLinkDO()
                .setCode(IdUtil.fastUUID())
                .setType(InviteLinkTypeEnum.LINK.getCode())
                .setStatus(CommonStatusEnum.ENABLE.getStatus())
                .setExpireTime(LocalDateTimeUtils.MAX)
                .setTenantId(tenantId);

        inviteLinkMapper.insert(inviteLinkDO);
        return inviteLinkDO.getCode();
    }

    @Override
    public String getLink(Long tenantId) {
        InviteLinkDO inviteLinkDO = inviteLinkMapper.selectByTenantIdAndLink(tenantId);
        return Optional.ofNullable(inviteLinkDO).map(InviteLinkDO::getCode).orElse(null);
    }

    @Override
    public void deleteByCode(String code) {
        InviteLinkDO inviteLinkDO = validate(code);
        inviteLinkMapper.deleteById(new InviteLinkDO().setId(inviteLinkDO.getId()).setCode(code));
    }

    @Override
    public void joinByCode(String code, Long userId) {
        InviteLinkDO inviteLinkDO = validate(code);
        Long tenantId = inviteLinkDO.getTenantId();
        tenantUserService.insert(tenantId, userId);
    }

    @Transactional
    @Override
    public void sendInviteMail(InviteSendMailReqVO reqVO, Long userId) {
        Long tenantId = reqVO.getTenantId();

        InviteLinkDO inviteLinkDO = new InviteLinkDO()
                .setCode(IdUtil.fastUUID())
                .setType(InviteLinkTypeEnum.MAIL.getCode())
                .setStatus(CommonStatusEnum.ENABLE.getStatus())
                .setExpireTime(LocalDateTime.now().plus(mailLinkExpireTime))
                .setTenantId(tenantId);
        inviteLinkMapper.insert(inviteLinkDO);

        TenantDO tenant = tenantService.getTenant(tenantId);
        String loginUrl = reqVO.getLoginUrl() + "?code=" + inviteLinkDO.getCode();
        for (String mail : reqVO.getMails()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("tenantName", tenant.getName());
            map.put("inviteLink", loginUrl);
            mailSendService.sendSingleMail(mail, userId, MailTemplateEnum.INVITE_JOIN_TEAM, map);
        }
    }


    private InviteLinkDO validate(String code) {
        InviteLinkDO inviteLinkDO = inviteLinkMapper.selectByCode(code);
        if (inviteLinkDO == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.INVITE_LINK_NO_EXISTS);
        }

        if (DateUtils.isExpired(inviteLinkDO.getExpireTime())) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.INVITE_LINK_EXPIRE);
        }

        return inviteLinkDO;
    }

}
