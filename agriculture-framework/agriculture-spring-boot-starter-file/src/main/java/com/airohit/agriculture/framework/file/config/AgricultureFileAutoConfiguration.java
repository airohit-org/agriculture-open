package com.airohit.agriculture.framework.file.config;

import com.airohit.agriculture.framework.file.core.client.FileClientFactory;
import com.airohit.agriculture.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 文件配置类
 *
 * @author shiminghao
 */
@AutoConfiguration
public class AgricultureFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
