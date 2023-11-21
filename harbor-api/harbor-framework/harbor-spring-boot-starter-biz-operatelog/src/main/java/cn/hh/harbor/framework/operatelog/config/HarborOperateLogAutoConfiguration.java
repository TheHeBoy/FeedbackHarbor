package cn.hh.harbor.framework.operatelog.config;

import cn.hh.harbor.framework.operatelog.core.aop.OperateLogAspect;
import cn.hh.harbor.framework.operatelog.core.service.OperateLogFrameworkService;
import cn.hh.harbor.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import cn.hh.harbor.module.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class HarborOperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
