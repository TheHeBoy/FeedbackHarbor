package cn.hh.harbor.module.harbor.dal.redis.like;

import cn.hh.harbor.module.harbor.enums.common.BusTypeEnum;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.hh.harbor.module.harbor.dal.redis.RedisKeyConstants.*;

/**
 * 用户点赞的缓存DAO层
 *
 * @author hehong
 * @date 2023-09-09
 */
@Repository
public class LikeRedisDAO {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedissonClient redissonClient;

    /**
     * @param uid             用户id
     * @param rid             关联id
     * @param likeAction      ture-点赞 false-取消点赞
     * @param busTypeEnum 业务枚举
     * @return {@link Boolean}
     */
    public Boolean isMember(Long uid, Long rid, boolean likeAction, BusTypeEnum busTypeEnum) {
        String redisKey = formatKey(String.valueOf(rid), likeAction, busTypeEnum);
        return stringRedisTemplate.opsForSet().isMember(redisKey, String.valueOf(uid));
    }

    /**
     * @param uid             用户id
     * @param rid             关联id
     * @param likeAction      ture-点赞 false-取消点赞
     * @param busTypeEnum 业务枚举
     * @return {@link Long}
     */
    public Long set(Long uid, Long rid, boolean likeAction, BusTypeEnum busTypeEnum) {
        String redisKey = formatKey(String.valueOf(rid), likeAction, busTypeEnum);
        return stringRedisTemplate.opsForSet().add(redisKey, String.valueOf(uid));
    }


    /**
     * @param uid             用户id
     * @param rid             关联id
     * @param likeAction      ture-点赞 false-取消点赞
     * @param busTypeEnum 业务枚举
     * @return {@link Long}
     */
    public Long remove(Long uid, Long rid, boolean likeAction, BusTypeEnum busTypeEnum) {
        String redisKey = formatKey(String.valueOf(rid), likeAction, busTypeEnum);
        return stringRedisTemplate.opsForSet().remove(redisKey, String.valueOf(uid));
    }

    /**
     * @param likeAction      ture-点赞 false-取消点赞
     * @param busTypeEnum 业务枚举
     * @return {@link Long}
     */
    public Long removeBatch(boolean likeAction, BusTypeEnum busTypeEnum) {
        Set<String> keys = list(likeAction, busTypeEnum);
        return stringRedisTemplate.delete(keys);
    }

    /**
     * @param likeAction      ture-点赞 false-取消点赞
     * @param busTypeEnum 业务枚举
     * @return {@link Set}<{@link String}>
     */
    public Set<String> list(boolean likeAction, BusTypeEnum busTypeEnum) {
        String pattern = formatKey("*", likeAction, busTypeEnum);
        RKeys keys = redissonClient.getKeys();
        Set<String> keyList = new HashSet<>();
        keys.getKeysByPattern(pattern).forEach(keyList::add);
        return keyList;
    }

    /**
     * @param key key值
     * @return {@link Set}<{@link Long}>
     */
    public Set<Long> sGet(String key) {
        return Objects.requireNonNull(stringRedisTemplate.opsForSet().members(key))
                .stream().map(Long::valueOf).collect(Collectors.toSet());
    }

    /**
     * @param rid             关联id
     * @param likeAction      ture-点赞 false-取消点赞
     * @param busTypeEnum 业务枚举
     * @return {@link Long}
     */
    public Long sSize(Long rid, boolean likeAction, BusTypeEnum busTypeEnum) {
        String redisKey = formatKey(String.valueOf(rid), likeAction, busTypeEnum);
        return stringRedisTemplate.opsForSet().size(redisKey);
    }

    private static String formatKey(String rid, boolean likeAction, BusTypeEnum busTypeEnum) {
        switch (busTypeEnum) {
            case FEEDBACK:
                if (likeAction) {
                    return String.format(FEEDBACK_LIKE, rid);
                } else {
                    return String.format(FEEDBACK_CANCEL_LIKE, rid);
                }
            case COMMENT:
            default:
                if (likeAction) {
                    return String.format(COMMENT_LIKE, rid);
                } else {
                    return String.format(COMMENT_CANCEL_LIKE, rid);
                }
        }
    }
}
