package cn.hh.harbor.module.system.service.mail;

import cn.hh.harbor.framework.common.exception.ServiceException;
import cn.hh.harbor.module.system.service.mail.vo.MailCaptchaSendReqVO;
import cn.hh.harbor.module.system.service.mail.vo.MailCaptchaUseReqVO;

import javax.validation.Valid;

public interface MailCaptchaService {

    /**
     * 创建邮箱验证码，并进行发送
     *
     * @param reqVO 发送请求
     */
    void sendMailCaptcha(@Valid MailCaptchaSendReqVO reqVO);

    /**
     * 验证邮箱验证码，并进行使用
     * 如果正确，则将验证码标记成已使用
     * 如果错误，则抛出 {@link ServiceException} 异常
     *
     * @param reqVO 使用请求
     */
    void useMailCaptcha(@Valid MailCaptchaUseReqVO reqVO);
}
