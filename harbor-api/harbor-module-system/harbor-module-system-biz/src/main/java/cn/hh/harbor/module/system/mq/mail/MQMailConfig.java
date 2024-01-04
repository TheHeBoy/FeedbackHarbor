package cn.hh.harbor.module.system.mq.mail;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cn.hh.harbor.module.system.mq.MQKeyConstants.SYSTEM_MAIL_QUEUE;

/**
 * 用于创建队列，交换机等
 *
 * @author hehong
 * @date 2023-12-23
 */
@EnableRabbit
@Configuration
public class MQMailConfig {

    @Bean
    public Queue systemMailQueue(){
        return new Queue(SYSTEM_MAIL_QUEUE);
    }

}
