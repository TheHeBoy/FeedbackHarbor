package cn.hh.harbor.module.system.service.mail.vo;

import cn.hh.harbor.framework.common.validation.InEnum;
import cn.hh.harbor.module.system.enums.mail.MailSceneEnum;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MailCaptchaUseReqVO {
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
    @InEnum(MailSceneEnum.class)
    private Integer scene;
    /**
     * 验证码
     */
    @NotEmpty(message = "验证码")
    private String captcha;
    /**
     * 使用 IP
     */
    @NotEmpty(message = "使用 IP 不能为空")
    private String usedIp;
}
