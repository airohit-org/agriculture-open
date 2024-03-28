package com.airohit.agriculture.framework.tenant.config;

import com.airohit.agriculture.framework.mybatis.core.util.MyBatisUtils;
import com.airohit.agriculture.framework.tenant.core.db.FarmDatabaseInterceptor;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/8/15 11:12
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "agriculture.tenant.farm", value = "enable", matchIfMissing = true)
// 允许使用 agriculture.tenant.enable=false 禁用多租户
@EnableConfigurationProperties({FarmTenantProperties.class})
public class AgricultureFarmTenantAutoConfiguration {

    @Bean
    public TenantLineInnerInterceptor farmTenantLineInnerInterceptor(FarmTenantProperties properties,
                                                                     MybatisPlusInterceptor interceptor) {
        TenantLineInnerInterceptor inner = new TenantLineInnerInterceptor(new FarmDatabaseInterceptor(properties));
        // 添加到 interceptor 中
        // 需要加在首个，主要是为了在分页插件前面。这个是 MyBatis Plus 的规定
        MyBatisUtils.addInterceptor(interceptor, inner, 1);
        return inner;
    }
}
