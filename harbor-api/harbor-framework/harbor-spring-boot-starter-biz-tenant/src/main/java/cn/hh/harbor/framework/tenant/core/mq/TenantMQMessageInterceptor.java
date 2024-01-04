package cn.hh.harbor.framework.tenant.core.mq;

import cn.hh.harbor.framework.mq.core.interceptor.MQMessageInterceptor;
import cn.hutool.core.util.StrUtil;
import cn.hh.harbor.framework.tenant.core.context.TenantContextHolder;
import org.springframework.amqp.core.Message;

import static cn.hh.harbor.framework.web.core.util.WebFrameworkUtils.HEADER_TENANT_ID;

/**
 * 多租户 {@link MQMessageInterceptor} 拦截器
 * <p>
 * 1. Producer 发送消息时，将 {@link TenantContextHolder} 租户编号，添加到消息的 Header 中
 * 2. Consumer 消费消息时，将消息的 Header 的租户编号，添加到 {@link TenantContextHolder} 中
 */
public class TenantMQMessageInterceptor implements MQMessageInterceptor {

    @Override
    public void sendMessageBefore(Message message) {
        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId != null) {
            message.getMessageProperties().setHeader(HEADER_TENANT_ID, tenantId.toString());
        }
    }

    @Override
    public void consumeMessageBefore(Message message) {
        String tenantIdStr = message.getMessageProperties().getHeader(HEADER_TENANT_ID);
        if (StrUtil.isNotEmpty(tenantIdStr)) {
            TenantContextHolder.setTenantId(Long.valueOf(tenantIdStr));
        }
    }

    @Override
    public void consumeMessageAfter(Message message) {
        // 注意，Consumer 是一个逻辑的入口，所以不考虑原本上下文就存在租户编号的情况
        TenantContextHolder.clear();
    }

}
