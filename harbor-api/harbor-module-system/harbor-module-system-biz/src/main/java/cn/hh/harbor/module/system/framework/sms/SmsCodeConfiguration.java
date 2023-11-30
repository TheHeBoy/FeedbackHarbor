package cn.hh.harbor.module.system.framework.sms;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MailCaptchaProperties.class)
public class SmsCodeConfiguration {
}
