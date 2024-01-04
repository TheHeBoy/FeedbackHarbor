package cn.hh.harbor.framework.mq.core.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * {@link RabbitListener}方法拦截器
 */
public class MQRabbitListenerInterceptor implements MethodInterceptor {

    private final MQMessageInterceptor mqMessageInterceptor;

    public MQRabbitListenerInterceptor(MQMessageInterceptor mqMessageInterceptor) {
        this.mqMessageInterceptor = mqMessageInterceptor;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        Message message = (Message) arguments[1];
        mqMessageInterceptor.consumeMessageBefore(message);
        Object proceed = invocation.proceed();
        mqMessageInterceptor.consumeMessageBefore(message);
        return proceed;
    }
}
