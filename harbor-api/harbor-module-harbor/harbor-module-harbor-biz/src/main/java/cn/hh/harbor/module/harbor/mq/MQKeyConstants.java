package cn.hh.harbor.module.harbor.mq;

/**
 * MQ 枚举类
 */
public interface MQKeyConstants {

    String HARBOR_FEEDBACK_TOPIC_EXCHANGE = "harbor.feedback.exchange";

    String HARBOR_FEEDBACK_UPDATE_QUEUE = "harbor.feedback.update.queue";

    String HARBOR_FEEDBACK_UPDATE_KEY = "harbor.feedback.update.key";
}
