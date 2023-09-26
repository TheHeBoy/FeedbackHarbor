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
     * KEY 格式：like:feedback:{rid}
     * VALUE 数据类型：String 用户id
     */
    String FEEDBACK_LIKE = "like:feedback:%s";

    /**
     * 用户反馈取消点赞
     * <p>
     * KEY 格式：cancel_like:feedback:{rid}
     * VALUE 数据类型：String 用户id
     */
    String FEEDBACK_CANCEL_LIKE = "cancel_like:feedback:%s";

    /**
     * 用户评论点赞
     * <p>
     * KEY 格式：like:comment:{rid}
     * VALUE 数据类型：String 用户id
     */
    String COMMENT_LIKE = "like:comment:%s";

    /**
     * 用户评论取消点赞
     * <p>
     * KEY 格式：cancel_like:comment:{rid}
     * VALUE 数据类型：String 用户id
     */
    String COMMENT_CANCEL_LIKE = "cancel_like:comment:%s";

}
