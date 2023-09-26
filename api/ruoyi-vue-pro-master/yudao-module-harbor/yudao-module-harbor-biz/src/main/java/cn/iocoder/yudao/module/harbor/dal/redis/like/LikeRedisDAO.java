package cn.iocoder.yudao.module.harbor.dal.redis.like;

import cn.iocoder.yudao.module.harbor.enums.like.LikeBusTypeEnum;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.module.harbor.dal.redis.RedisKeyConstants.*;

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

    public Boolean isMember(Long uid, Long rid, boolean likeAction, LikeBusTypeEnum likeBusTypeEnum) {
        String redisKey = formatKey(String.valueOf(rid), likeAction, likeBusTypeEnum);
        return stringRedisTemplate.opsForSet().isMember(redisKey, String.valueOf(uid));
    }

    public Long set(Long uid, Long rid, boolean likeAction, LikeBusTypeEnum likeBusTypeEnum) {
        String redisKey = formatKey(String.valueOf(rid), likeAction, likeBusTypeEnum);
        return stringRedisTemplate.opsForSet().add(redisKey, String.valueOf(uid));
    }


    public Long remove(Long uid, Long rid, boolean likeAction, LikeBusTypeEnum likeBusTypeEnum) {
        String redisKey = formatKey(String.valueOf(rid), likeAction, likeBusTypeEnum);
        return stringRedisTemplate.opsForSet().remove(redisKey, String.valueOf(uid));
    }

    public Long removeBatch(boolean likeAction, LikeBusTypeEnum likeBusTypeEnum) {
        Set<String> keys = list(likeAction, likeBusTypeEnum);
        return stringRedisTemplate.delete(keys);
    }

    public Set<String> list(boolean likeAction, LikeBusTypeEnum likeBusTypeEnum) {
        String pattern = formatKey("*", likeAction, likeBusTypeEnum);
        RKeys keys = redissonClient.getKeys();
        Set<String> keyList = new HashSet<>();
        keys.getKeysByPattern(pattern).forEach(keyList::add);
        return keyList;
    }

    public Set<Long> sGet(String key) {
        return Objects.requireNonNull(stringRedisTemplate.opsForSet().members(key))
                .stream().map(Long::valueOf).collect(Collectors.toSet());
    }

    public Long sSize(Long rid, boolean likeAction, LikeBusTypeEnum likeBusTypeEnum) {
        String redisKey = formatKey(String.valueOf(rid), likeAction, likeBusTypeEnum);
        return stringRedisTemplate.opsForSet().size(redisKey);
    }

    private static String formatKey(String rid, boolean likeAction, LikeBusTypeEnum busTypeEnum) {
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
