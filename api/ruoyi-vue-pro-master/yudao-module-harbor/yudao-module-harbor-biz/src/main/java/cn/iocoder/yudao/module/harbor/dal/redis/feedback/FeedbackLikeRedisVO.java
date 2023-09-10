package cn.iocoder.yudao.module.harbor.dal.redis.feedback;

import lombok.Data;

/**
 * 用户点赞的缓存对象
 * @author hehong
 * @date 2023-09-09
 */
@Data
public class FeedbackLikeRedisVO {

    /**
     * 用户id
     */
    private String uid;

    /**
     * 反馈id
     */
    private String feedbackId;
}
