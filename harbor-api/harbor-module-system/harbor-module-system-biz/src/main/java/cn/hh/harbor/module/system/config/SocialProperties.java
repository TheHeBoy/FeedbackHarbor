package cn.hh.harbor.module.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.time.Duration;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "harbor.social")
@Validated
@Data
public class SocialProperties {

    @NotEmpty(message = "社交client不能为空")
    private Map<String, Map<String, SocialApp>> type;

    private SocialProperties.Proxy proxy;

    @Data
    public static class SocialApp {
        private String clientId;
        private String clientSecret;
    }

    @Data
    public static class Proxy {
        private Integer timeout;
        private String hostname;
        private Integer port;
        private String type;
    }
}
