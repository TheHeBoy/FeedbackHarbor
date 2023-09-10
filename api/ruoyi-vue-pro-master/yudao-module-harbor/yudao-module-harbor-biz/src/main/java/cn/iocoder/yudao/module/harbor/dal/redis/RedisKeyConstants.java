package cn.iocoder.yudao.module.harbor.dal.redis;

/**
 * Harbor Redis Key 枚举类
 *
 *
 */
public interface RedisKeyConstants {


    /**
     * 用户反馈点赞
     * <p>
     * KEY 格式：feedback_like:uid:{feedbackId}
     * VALUE 数据类型：String 用户id
     */
    String FEEDBACK_LIKE = "feedback_like:uid:%s";

    /**
     * 用户反馈取消点赞
     * <p>
     * KEY 格式：feedback_cancel_like:uid:{feedbackId}
     * VALUE 数据类型：String 用户id
     */
    String FEEDBACK_CANCEL_LIKE = "feedback_cancel_like:uid:%s";
}
