package cn.hh.harbor.framework.tracer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * BizTracer配置类
 *
 *  麻薯
 */
@ConfigurationProperties("harbor.tracer")
@Data
public class TracerProperties {
}
