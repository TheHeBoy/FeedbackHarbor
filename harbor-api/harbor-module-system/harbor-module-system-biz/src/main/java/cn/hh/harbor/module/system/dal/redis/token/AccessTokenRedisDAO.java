package cn.hh.harbor.module.system.dal.redis.token;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hh.harbor.framework.common.util.json.JsonUtils;
import cn.hh.harbor.module.system.dal.dataobject.token.TokenAccessDO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static cn.hh.harbor.module.system.dal.redis.RedisKeyConstants.ACCESS_TOKEN;

/**
 * {@link TokenAccessDO} 的 RedisDAO
 */
@Repository
public class AccessTokenRedisDAO {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public TokenAccessDO get(String accessToken) {
        String redisKey = formatKey(accessToken);
        return JsonUtils.parseObject(stringRedisTemplate.opsForValue().get(redisKey), TokenAccessDO.class);
    }

    public void set(TokenAccessDO accessTokenDO) {
        String redisKey = formatKey(accessTokenDO.getAccessToken());
        // 清理多余字段，避免缓存
        accessTokenDO.setUpdater(null).setUpdateTime(null).setCreateTime(null).setCreator(null).setDeleted(null);
        long time = LocalDateTimeUtil.between(LocalDateTime.now(), accessTokenDO.getExpiresTime(), ChronoUnit.SECONDS);
        if (time > 0) {
            stringRedisTemplate.opsForValue().set(redisKey, JSONUtil.toJsonStr(accessTokenDO), time, TimeUnit.SECONDS);
        }
    }

    public void delete(String accessToken) {
        String redisKey = formatKey(accessToken);
        stringRedisTemplate.delete(redisKey);
    }

    public void update(String accessToken, TokenAccessDO accessDO) {
        getSelf().delete(accessToken);
        getSelf().set(accessDO);
    }

    private static String formatKey(String accessToken) {
        return String.format(ACCESS_TOKEN, accessToken);
    }

    private AccessTokenRedisDAO getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
