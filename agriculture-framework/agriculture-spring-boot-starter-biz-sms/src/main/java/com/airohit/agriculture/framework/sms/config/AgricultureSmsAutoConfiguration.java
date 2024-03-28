package com.airohit.agriculture.framework.sms.config;

import com.airohit.agriculture.framework.sms.core.client.SmsClientFactory;
import com.airohit.agriculture.framework.sms.core.client.impl.SmsClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 短信配置类
 *
 * @author shiminghao
 */
@AutoConfiguration
public class AgricultureSmsAutoConfiguration {

    @Bean
    public SmsClientFactory smsClientFactory() {
        return new SmsClientFactoryImpl();
    }

}
