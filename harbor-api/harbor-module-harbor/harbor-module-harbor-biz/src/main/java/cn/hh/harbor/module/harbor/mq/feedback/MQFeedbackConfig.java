package cn.hh.harbor.module.harbor.mq.feedback;

import cn.hh.harbor.module.harbor.mq.MQKeyConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用于创建队列，交换机等
 *
 * @author hehong
 * @date 2023-12-23
 */
@EnableRabbit
@Configuration
public class MQFeedbackConfig {

    @Bean
    public TopicExchange harborFeedbackTopicExchange() {
        return new TopicExchange(MQKeyConstants.HARBOR_FEEDBACK_TOPIC_EXCHANGE, true, false);
    }

    @Bean
    public Queue harborFeedbackUpdateQueue() {
        return new Queue(MQKeyConstants.HARBOR_FEEDBACK_UPDATE_QUEUE, true);
    }

    @Bean
    public Binding harborFeedbackBinding() {
        return BindingBuilder.bind(harborFeedbackUpdateQueue()).to(harborFeedbackTopicExchange())
                .with(MQKeyConstants.HARBOR_FEEDBACK_UPDATE_KEY);
    }
}
