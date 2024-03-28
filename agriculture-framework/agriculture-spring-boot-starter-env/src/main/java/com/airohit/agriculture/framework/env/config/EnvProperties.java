package com.airohit.agriculture.framework.env.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 环境配置
 *
 * @author shiminghao
 */
@ConfigurationProperties(prefix = "agriculture.env")
@Data
public class EnvProperties {

    public static final String TAG_KEY = "agriculture.env.tag";

    /**
     * 环境标签
     */
    private String tag;

}
