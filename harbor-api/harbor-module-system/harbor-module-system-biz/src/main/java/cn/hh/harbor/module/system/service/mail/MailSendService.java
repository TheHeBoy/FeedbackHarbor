package cn.hh.harbor.module.system.service.mail;

import cn.hh.harbor.module.system.mq.mail.MailSendMessage;

import java.util.Map;

/**
 * 邮件发送 Service 接口
 * <p>
 * wangjingyi
 *
 * @since 2022-03-21
 */
public interface MailSendService {

    /**
     * 发送单条邮件给用户
     *
     * @param mail           邮箱
     * @param userId         用户编码
     * @param templateCode   邮件模版编码
     * @param templateParams 邮件模版参数
     * @return 发送日志编号
     */
    Long sendSingleMail(String mail, Long userId, String templateCode, Map<String, Object> templateParams);

    /**
     * 执行真正的邮件发送
     * 注意，该方法仅仅提供给 MQ Consumer 使用
     *
     * @param message 邮件
     */
    void doSendMail(MailSendMessage message);

}
