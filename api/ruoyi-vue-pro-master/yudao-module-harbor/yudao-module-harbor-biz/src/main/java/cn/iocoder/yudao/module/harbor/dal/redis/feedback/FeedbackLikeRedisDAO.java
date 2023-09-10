package cn.iocoder.yudao.module.harbor.dal.redis.feedback;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.module.harbor.enums.feedback.FeedbackLikeEnum;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.module.harbor.dal.redis.RedisKeyConstants.FEEDBACK_CANCEL_LIKE;
import static cn.iocoder.yudao.module.harbor.dal.redis.RedisKeyConstants.FEEDBACK_LIKE;
import static cn.iocoder.yudao.module.harbor.enums.feedback.FeedbackLikeEnum.LIKED;

/**
 * 用户点赞的缓存DAO层
 *
 * @author hehong
 * @date 2023-09-09
 */
@Repository
public class FeedbackLikeRedisDAO {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedissonClient redissonClient;

    public Boolean isMember(FeedbackLikeRedisVO entity, FeedbackLikeEnum likeEnum) {
        String redisKey = formatKey(entity.getFeedbackId(), likeEnum);
        return stringRedisTemplate.opsForSet().isMember(redisKey, entity.getUid());
    }

    public Long set(FeedbackLikeRedisVO entity, FeedbackLikeEnum likeEnum) {
        String redisKey = formatKey(entity.getFeedbackId(), likeEnum);
        return stringRedisTemplate.opsForSet().add(redisKey, entity.getUid());
    }


    public Long remove(FeedbackLikeRedisVO entity, FeedbackLikeEnum likeEnum) {
        String redisKey = formatKey(entity.getFeedbackId(), likeEnum);
        return stringRedisTemplate.opsForSet().remove(redisKey, entity.getUid());
    }

    public Set<String> list(FeedbackLikeEnum likeEnum) {
        String pattern = formatKey( "*", likeEnum);
        RKeys keys = redissonClient.getKeys();
        Set<String> keyList = new HashSet<>();
        keys.getKeysByPattern(pattern).forEach(keyList::add);
        return keyList;
    }

    public Set<Long> sGet(String key) {
        return Objects.requireNonNull(stringRedisTemplate.opsForSet().members(key))
                .stream().map(Long::valueOf).collect(Collectors.toSet());
    }

    public Long sSize(String feedbackId,FeedbackLikeEnum likeEnum){
        String redisKey = formatKey(feedbackId, likeEnum);
        return stringRedisTemplate.opsForSet().size(redisKey);
    }

    private static String formatKey(String feedbackId, FeedbackLikeEnum likeEnum) {
        if (ObjectUtil.equal(LIKED, likeEnum)) {
            return String.format(FEEDBACK_LIKE, feedbackId);
        } else {
            return String.format(FEEDBACK_CANCEL_LIKE, feedbackId);
        }
    }
}
