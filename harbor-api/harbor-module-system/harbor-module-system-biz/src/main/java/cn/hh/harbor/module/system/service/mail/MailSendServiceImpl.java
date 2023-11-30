package cn.hh.harbor.module.system.service.mail;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hh.harbor.framework.common.enums.CommonStatusEnum;
import cn.hh.harbor.framework.common.enums.UserTypeEnum;
import cn.hh.harbor.module.system.convert.mail.MailAccountConvert;
import cn.hh.harbor.module.system.dal.dataobject.mail.MailAccountDO;
import cn.hh.harbor.module.system.dal.dataobject.mail.MailTemplateDO;
import cn.hh.harbor.module.system.dal.dataobject.user.UserDO;
import cn.hh.harbor.module.system.mq.message.mail.MailSendMessage;
import cn.hh.harbor.module.system.mq.producer.mail.MailProducer;
import cn.hh.harbor.module.system.service.user.UserService;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Map;

import static cn.hh.harbor.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.hh.harbor.module.system.enums.ErrorCodeConstants.*;

/**
 * 邮箱发送 Service 实现类
 * <p>
 * wangjingyi
 *
 * @since 2022-03-21
 */
@Service
@Validated
@Slf4j
public class MailSendServiceImpl implements MailSendService {

    @Resource
    private UserService userService;
    @Resource
    private MailAccountService mailAccountService;
    @Resource
    private MailTemplateService mailTemplateService;
    @Resource
    private MailLogService mailLogService;
    @Resource
    private MailProducer mailProducer;

    @Override
    public Long sendSingleMail(String mail, Long userId, String templateCode, Map<String, Object> templateParams) {
        // 校验邮箱模版是否合法
        MailTemplateDO template = validateMailTemplate(templateCode);
        // 校验邮箱账号是否合法
        MailAccountDO account = validateMailAccount(template.getAccountId());

        // 校验邮箱是否存在
        mail = validateMail(mail);
        validateTemplateParams(template, templateParams);

        // 创建发送日志。如果模板被禁用，则不发送邮件，只记录日志
        Boolean isSend = CommonStatusEnum.ENABLE.getStatus().equals(template.getStatus());
        String title = mailTemplateService.formatMailTemplateContent(template.getTitle(), templateParams);
        String content = mailTemplateService.formatMailTemplateContent(template.getContent(), templateParams);
        Long sendLogId = mailLogService.createMailLog(userId, mail, account, template, content, templateParams, isSend);
        // 发送 MQ 消息，异步执行发送邮件
        if (isSend) {
            mailProducer.sendMailSendMessage(sendLogId, mail, account.getId(),
                    template.getNickname(), title, content);
        }
        return sendLogId;
    }

    @Override
    public void doSendMail(MailSendMessage message) {
        // 1. 创建发送账号
        MailAccountDO account = validateMailAccount(message.getAccountId());
        MailAccount mailAccount = MailAccountConvert.INSTANCE.convert(account, message.getNickname());
        // 2. 发送邮件
        try {
            String messageId = MailUtil.send(mailAccount, message.getMail(),
                    message.getTitle(), message.getContent(), true);
            // 3. 更新结果（成功）
            mailLogService.updateMailSendResult(message.getLogId(), messageId, null);
        } catch (Exception e) {
            // 3. 更新结果（异常）
            mailLogService.updateMailSendResult(message.getLogId(), null, e);
        }
    }

    @VisibleForTesting
    MailTemplateDO validateMailTemplate(String templateCode) {
        // 获得邮件模板。考虑到效率，从缓存中获取
        MailTemplateDO template = mailTemplateService.getMailTemplateByCodeFromCache(templateCode);
        // 邮件模板不存在
        if (template == null) {
            throw exception(MAIL_TEMPLATE_NOT_EXISTS);
        }
        return template;
    }

    @VisibleForTesting
    MailAccountDO validateMailAccount(Long accountId) {
        // 获得邮箱账号。考虑到效率，从缓存中获取
        MailAccountDO account = mailAccountService.getMailAccountFromCache(accountId);
        // 邮箱账号不存在
        if (account == null) {
            throw exception(MAIL_ACCOUNT_NOT_EXISTS);
        }
        return account;
    }

    @VisibleForTesting
    String validateMail(String mail) {
        if (StrUtil.isEmpty(mail)) {
            throw exception(MAIL_SEND_MAIL_NOT_EXISTS);
        }
        return mail;
    }

    /**
     * 校验邮件参数是否确实
     *
     * @param template       邮箱模板
     * @param templateParams 参数列表
     */
    @VisibleForTesting
    void validateTemplateParams(MailTemplateDO template, Map<String, Object> templateParams) {
        template.getParams().forEach(key -> {
            Object value = templateParams.get(key);
            if (value == null) {
                throw exception(MAIL_SEND_TEMPLATE_PARAM_MISS, key);
            }
        });
    }

}
