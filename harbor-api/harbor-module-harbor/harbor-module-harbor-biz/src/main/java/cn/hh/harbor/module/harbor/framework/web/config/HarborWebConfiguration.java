package cn.hh.harbor.module.harbor.framework.web.config;

import cn.hh.harbor.framework.swagger.config.HarborSwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
public class HarborWebConfiguration {

    /**
     * Harbor 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi HarborGroupedOpenApi() {
        return HarborSwaggerAutoConfiguration.buildGroupedOpenApi("harbor");
    }

}
