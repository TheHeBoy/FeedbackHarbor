package cn.hh.harbor.framework.mq.config;

import cn.hh.harbor.framework.mq.core.interceptor.MQMessageInterceptor;
import cn.hh.harbor.framework.mq.core.interceptor.MQRabbitListenerInterceptor;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;

/**
 * 消息队列配置类
 */
@AutoConfiguration
public class HarborMQAutoConfiguration {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory,
            MQMessageInterceptor mqMessageInterceptor) {
        simpleRabbitListenerContainerFactory.setAdviceChain(new MQRabbitListenerInterceptor(mqMessageInterceptor));
        return simpleRabbitListenerContainerFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(RabbitTemplateConfigurer configurer, ConnectionFactory connectionFactory,
                                         MQMessageInterceptor mqMessageInterceptor) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        configurer.configure(rabbitTemplate, connectionFactory);
        rabbitTemplate.addBeforePublishPostProcessors((message -> {
            mqMessageInterceptor.sendMessageBefore(message);
            return message;
        }));
        return rabbitTemplate;
    }
}
