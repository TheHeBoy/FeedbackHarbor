package cn.hh.harbor.framework.lock4j.config;

import cn.hh.harbor.framework.lock4j.core.DefaultLockFailureStrategy;
import com.baomidou.lock.spring.boot.autoconfigure.LockAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(before = LockAutoConfiguration.class)
public class HarborLock4jConfiguration {

    @Bean
    public DefaultLockFailureStrategy lockFailureStrategy() {
        return new DefaultLockFailureStrategy();
    }

}
