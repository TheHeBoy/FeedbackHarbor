package cn.hh.harbor.module.system.service.mail.vo;

import cn.hh.harbor.framework.common.validation.InEnum;
import cn.hh.harbor.module.system.enums.mail.MailCaptchaSceneEnum;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
public class MailCaptchaSendReqVO {
    /**
     * 邮箱
     */
    @Email
    @NotEmpty(message = "邮箱不能为空")
    private String mail;
    /**
     * 发送场景
     */
    @NotNull(message = "发送场景不能为空")
    @InEnum(MailCaptchaSceneEnum.class)
    private Integer scene;
    /**
     * 发送 IP
     */
    @NotEmpty(message = "发送 IP 不能为空")
    private String createIp;
}
