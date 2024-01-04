package cn.hh.harbor.module.system.mq.mail;

import cn.hh.harbor.module.system.mq.MQKeyConstants;
import cn.hh.harbor.module.system.mq.mail.MailSendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Mail 邮件相关消息的 Producer
 */
@Slf4j
@Component
public class MailProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送 {@link MailSendMessage} 消息
     *
     * @param sendLogId 发送日志编码
     * @param mail      接收邮件地址
     * @param accountId 邮件账号编号
     * @param nickname  邮件发件人
     * @param title     邮件标题
     * @param content   邮件内容
     */

    public void sendMailSendMessage(Long sendLogId, String mail, Long accountId,
                                    String nickname, String title, String content) {
        MailSendMessage message = new MailSendMessage()
                .setLogId(sendLogId).setMail(mail).setAccountId(accountId)
                .setNickname(nickname).setTitle(title).setContent(content);
        rabbitTemplate.convertAndSend(MQKeyConstants.SYSTEM_MAIL_QUEUE, message);
    }

}
