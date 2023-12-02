package cn.hh.harbor.module.system.dal.dataobject.mail;

import cn.hh.harbor.framework.mybatis.core.dataobject.BaseDO;
import cn.hh.harbor.module.system.enums.mail.MailCaptchaSceneEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 邮箱验证码 DO
 */
@TableName("system_mail_captcha")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailCaptchaDO extends BaseDO {

    /**
     * 编号
     */
    private Long id;
    /**
     * 邮箱
     */
    private String mail;
    /**
     * 验证码
     */
    private String captcha;
    /**
     * 发送场景
     * <p>
     * 枚举 {@link MailCaptchaSceneEnum}
     */
    private Integer scene;
    /**
     * 创建 IP
     */
    private String createIp;
    /**
     * 今日发送的第几条
     */
    private Integer todayIndex;
    /**
     * 是否使用
     */
    private Boolean used;
    /**
     * 使用时间
     */
    private LocalDateTime usedTime;
    /**
     * 使用 IP
     */
    private String usedIp;

}
