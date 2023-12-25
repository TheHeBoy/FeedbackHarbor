package cn.hh.harbor.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * 项目的启动类
 *
 *
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${harbor.info.base-package}
@SpringBootApplication(scanBasePackages = {"${harbor.info.base-package}.server", "${harbor.info.base-package}.module"})
@EnableElasticsearchRepositories(basePackages = {"${harbor.info.base-package}.server", "${harbor.info.base-package}.module"})
public class HarborServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HarborServerApplication.class, args);
    }

}
