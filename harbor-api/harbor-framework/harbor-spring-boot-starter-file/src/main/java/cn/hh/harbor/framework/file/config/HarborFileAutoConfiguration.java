package cn.hh.harbor.framework.file.config;

import cn.hh.harbor.framework.file.core.client.FileClientFactory;
import cn.hh.harbor.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 文件配置类
 *
 *
 */
@AutoConfiguration
public class HarborFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
