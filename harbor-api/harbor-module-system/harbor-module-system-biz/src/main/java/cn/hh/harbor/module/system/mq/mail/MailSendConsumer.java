package cn.hh.harbor.module.system.mq.mail;

import cn.hh.harbor.framework.tenant.core.context.TenantContextHolder;
import cn.hh.harbor.module.system.mq.MQKeyConstants;
import cn.hh.harbor.module.system.service.mail.MailSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 针对 {@link MailSendMessage} 的消费者
 */
@EnableRabbit
@Slf4j
@Component
public class MailSendConsumer {

    @Resource
    private MailSendService mailSendService;

    @RabbitListener(queues = MQKeyConstants.SYSTEM_MAIL_QUEUE)
    public void onMessage(MailSendMessage message) {
        log.info("[onMessage][消息内容({})]", message);
        mailSendService.doSendMail(message);
    }

}
