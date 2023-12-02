package cn.hh.harbor.module.system.enums.mail;

/**
 * 邮件模板编码
 *
 * @author hehong
 * @date 2023-11-29
 */
public interface MailTemplateEnum {
    /**
     * 邮箱注册-验证码
     */
    String REGISTER_CAPTCHA = "register_captcha";
    /**
     * 忘记密码-验证码
     */
    String RESET_PASSWD_CAPTCHA = "reset_passwd_captcha";
    /**
     * 邀请邮件
     */
    String INVITE_JOIN_TEAM = "invite_join_team";
}
