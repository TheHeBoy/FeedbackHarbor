package cn.hh.harbor.module.system.service.mail;

import cn.hh.harbor.module.system.dal.dataobject.mail.MailCaptchaDO;
import cn.hh.harbor.module.system.dal.mysql.mail.MailCaptchaMapper;
import cn.hh.harbor.module.system.enums.mail.MailCaptchaSceneEnum;
import cn.hh.harbor.module.system.framework.mail.MailCaptchaProperties;
import cn.hh.harbor.module.system.service.mail.vo.MailCaptchaSendReqVO;
import cn.hh.harbor.module.system.service.mail.vo.MailCaptchaUseReqVO;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static cn.hh.harbor.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.hh.harbor.framework.common.util.date.DateUtils.isToday;
import static cn.hh.harbor.module.system.enums.ErrorCodeConstants.*;
import static cn.hutool.core.util.RandomUtil.randomInt;

@Validated
@Service
public class MailCaptchaServiceImpl implements MailCaptchaService {
    @Resource
    private MailCaptchaProperties mailCaptchaProperties;

    @Resource
    private MailCaptchaMapper mailCaptchaMapper;

    @Resource
    private MailSendService mailSendService;

    @Override
    public void sendMailCaptcha(MailCaptchaSendReqVO reqVO) {
        MailCaptchaSceneEnum sceneEnum = MailCaptchaSceneEnum.getCodeByScene(reqVO.getScene());
        // 创建验证码
        String captcha = createCaptcha(reqVO.getMail(), reqVO.getScene(), reqVO.getCreateIp());
        // 发送验证码
        mailSendService.sendSingleMail(reqVO.getMail(), null, sceneEnum.getTemplateCode(), MapUtil.of("captcha", captcha));
    }

    @Override
    public void useMailCaptcha(MailCaptchaUseReqVO reqVO) {
        // 检测验证码是否有效
        MailCaptchaDO lastCaptcha = validateMailCaptcha(reqVO.getMail(), reqVO.getCaptcha(), reqVO.getScene());
        // 使用验证码
        mailCaptchaMapper.updateById(MailCaptchaDO.builder()
                .id(lastCaptcha.getId())
                .used(true)
                .usedTime(LocalDateTime.now())
                .usedIp(reqVO.getUsedIp())
                .build());
    }

    private String createCaptcha(String email, Integer scene, String ip) {
        // 校验是否可以发送验证码，不用筛选场景
        MailCaptchaDO lastCaptcha = mailCaptchaMapper.selectLastByMail(email, null, null);
        if (lastCaptcha != null) {
            if (LocalDateTimeUtil.between(lastCaptcha.getCreateTime(), LocalDateTime.now()).toMillis()
                    < mailCaptchaProperties.getSendFrequency().toMillis()) { // 发送过于频繁
                throw exception(MAIL_CAPTCHA_SEND_TOO_FAST);
            }
            if (isToday(lastCaptcha.getCreateTime()) && // 必须是今天，才能计算超过当天的上限
                    lastCaptcha.getTodayIndex() >= mailCaptchaProperties.getSendMaximumQuantityPerDay()) { // 超过当天发送的上限。
                throw exception(MAIL_CAPTCHA_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY);
            }
        }

        // 创建验证码记录
        String captcha = String.valueOf(randomInt(mailCaptchaProperties.getBeginCode(), mailCaptchaProperties.getEndCode() + 1));
        MailCaptchaDO newMailCaptcha = MailCaptchaDO.builder()
                .mail(email)
                .captcha(captcha)
                .scene(scene)
                .todayIndex(lastCaptcha != null && isToday(lastCaptcha.getCreateTime()) ? lastCaptcha.getTodayIndex() + 1 : 1)
                .createIp(ip)
                .used(false)
                .build();
        mailCaptchaMapper.insert(newMailCaptcha);
        return captcha;
    }

    private MailCaptchaDO validateMailCaptcha(String mobile, String captcha, Integer scene) {
        // 校验验证码
        MailCaptchaDO lastCaptcha = mailCaptchaMapper.selectLastByMail(mobile, captcha, scene);
        // 若验证码不存在，抛出异常
        if (lastCaptcha == null) {
            throw exception(MAIL_CAPTCHA_NOT_FOUND);
        }
        // 超过时间
        if (LocalDateTimeUtil.between(lastCaptcha.getCreateTime(), LocalDateTime.now()).toMillis()
                >= mailCaptchaProperties.getExpireTimes().toMillis()) { // 验证码已过期
            throw exception(MAIL_CAPTCHA_EXPIRED);
        }
        // 判断验证码是否已被使用
        if (Boolean.TRUE.equals(lastCaptcha.getUsed())) {
            throw exception(MAIL_CAPTCHA_USED);
        }
        return lastCaptcha;
    }
}
