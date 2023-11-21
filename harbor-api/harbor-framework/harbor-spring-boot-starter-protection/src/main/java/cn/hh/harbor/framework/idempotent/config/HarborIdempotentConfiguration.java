package cn.hh.harbor.framework.idempotent.config;

import cn.hh.harbor.framework.idempotent.core.aop.IdempotentAspect;
import cn.hh.harbor.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import cn.hh.harbor.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import cn.hh.harbor.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import cn.hh.harbor.framework.idempotent.core.redis.IdempotentRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import cn.hh.harbor.framework.redis.config.HarborRedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = HarborRedisAutoConfiguration.class)
public class HarborIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
