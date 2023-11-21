package cn.hh.harbor.module.system.framework.web.config;

import cn.hh.harbor.framework.swagger.config.HarborSwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system 模块的 web 组件的 Configuration
 *
 *
 */
@Configuration(proxyBeanMethods = false)
public class SystemWebConfiguration {

    /**
     * system 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi systemGroupedOpenApi() {
        return HarborSwaggerAutoConfiguration.buildGroupedOpenApi("system");
    }

}
