package cn.hh.harbor.framework.sms.config;

import cn.hh.harbor.framework.sms.core.client.SmsClientFactory;
import cn.hh.harbor.framework.sms.core.client.impl.SmsClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 短信配置类
 *
 *
 */
@AutoConfiguration
public class HarborSmsAutoConfiguration {

    @Bean
    public SmsClientFactory smsClientFactory() {
        return new SmsClientFactoryImpl();
    }

}
