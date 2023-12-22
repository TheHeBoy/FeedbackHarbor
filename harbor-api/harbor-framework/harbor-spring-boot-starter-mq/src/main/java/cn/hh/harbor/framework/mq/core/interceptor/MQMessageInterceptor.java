package cn.hh.harbor.framework.mq.core.interceptor;

import org.springframework.amqp.core.Message;

/**
 * 通过拦截器，作为插件机制，实现拓展。
 * 例如说，多租户场景下的 MQ 消息处理
 */
public interface MQMessageInterceptor {

    default void sendMessageBefore(Message message) {
    }

    default void consumeMessageBefore(Message message) {
    }

    default void consumeMessageAfter(Message message) {
    }

}
