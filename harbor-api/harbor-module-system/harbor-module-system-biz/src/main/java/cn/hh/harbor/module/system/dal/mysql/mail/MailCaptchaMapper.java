package cn.hh.harbor.module.system.dal.mysql.mail;

import cn.hh.harbor.framework.mybatis.core.mapper.BaseMapperX;
import cn.hh.harbor.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.hh.harbor.module.system.dal.dataobject.mail.MailCaptchaDO;
import cn.hutool.core.collection.CollUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MailCaptchaMapper extends BaseMapperX<MailCaptchaDO> {

    /**
     * 获得手机号的最后一个手机验证码
     */
    default MailCaptchaDO selectLastByMail(String mail, String captcha, Integer scene) {
        List<MailCaptchaDO> mailCaptchaDOS = selectList(new LambdaQueryWrapperX<MailCaptchaDO>()
                .eq(MailCaptchaDO::getMail, mail)
                .eqIfPresent(MailCaptchaDO::getScene, scene)
                .eqIfPresent(MailCaptchaDO::getCaptcha, captcha)
                .orderByDesc(MailCaptchaDO::getId));
        return CollUtil.isEmpty(mailCaptchaDOS) ? null : mailCaptchaDOS.get(0);
    }
}
