package cn.hh.harbor.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目的启动类
 *
 * 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
 * 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
 * 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
 *
 *
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${harbor.info.base-package}
@SpringBootApplication(scanBasePackages = {"${harbor.info.base-package}.server", "${harbor.info.base-package}.module"})
public class HarborServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HarborServerApplication.class, args);
    }

}
