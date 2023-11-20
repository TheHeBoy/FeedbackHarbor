package cn.iocoder.yudao.framework.mybatis.config;

import cn.iocoder.yudao.framework.common.enums.SystemIdEnum;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * Redis id 生成
 * <p>
 *
 * @author hehong
 * @date 2023-11-18
 */
@Slf4j
@AutoConfiguration
public class RedisIdGenerator implements IdentifierGenerator {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Long nextId(Object entity) {
        Long count = stringRedisTemplate.opsForValue().increment("id_worker");
        if (count == null) {
            log.error("Redis Id生成失败");
            return null;
        }

        // 3.拼接并返回
        return SystemIdEnum.SYSTEM_ID + count;
    }
}