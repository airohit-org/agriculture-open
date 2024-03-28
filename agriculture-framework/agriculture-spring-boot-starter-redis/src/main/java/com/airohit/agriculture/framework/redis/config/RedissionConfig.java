package com.airohit.agriculture.framework.redis.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redission配置类
 *
 * @author tianweixun
 * @date 2020/04/02
 */

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@Slf4j
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedissionConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String url = "redis://" + redisProperties.getHost() + ":" + redisProperties.getPort();
        if (StrUtil.isBlank(redisProperties.getPassword())) {
            redisProperties.setPassword(null);
        }
        config.useSingleServer()
                .setAddress(url)
                .setPassword(redisProperties.getPassword())
                .setDatabase(redisProperties.getDatabase());
        return Redisson.create(config);
    }

}

