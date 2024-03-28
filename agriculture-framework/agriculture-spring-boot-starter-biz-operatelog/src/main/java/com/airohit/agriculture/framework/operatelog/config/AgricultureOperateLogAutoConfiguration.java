package com.airohit.agriculture.framework.operatelog.config;

import com.airohit.agriculture.framework.operatelog.core.aop.OperateLogAspect;
import com.airohit.agriculture.framework.operatelog.core.service.OperateLogFrameworkService;
import com.airohit.agriculture.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import com.airohit.agriculture.module.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class AgricultureOperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
