package com.airohit.agriculture.framework.env.config;

import com.airohit.agriculture.framework.env.core.fegin.EnvRequestInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 多环境的 RPC 组件的自动配置
 *
 * @author shiminghao
 */
@AutoConfiguration
@EnableConfigurationProperties(EnvProperties.class)
public class AgricultureEnvRpcAutoConfiguration {

    // ========== Feign 相关 ==========

    // TODO @shiminghao：由于 loadBalancerClientFactoryBeanPostProcessor 拦截不到 LoadBalancerClientFactory，所以采用 loadBalancerClientFactory 实现
//    @Bean
//    public BeanPostProcessor loadBalancerClientFactoryBeanPostProcessor(LoadBalancerClientsProperties properties) {
//        return new BeanPostProcessor() {
//            @Override
//            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//                if (!(bean instanceof LoadBalancerClientFactory)) {
//                    return bean;
//                }
//                return bean;
//            }
//        };
//    }

//    /**
//     * 创建 {@link EnvLoadBalancerClientFactory} Bean
//     *
//     * 参考 {@link org.springframework.cloud.loadbalancer.config.LoadBalancerAutoConfiguration#loadBalancerClientFactory(LoadBalancerClientsProperties)} 方法
//     */
//    @Bean
//    public LoadBalancerClientFactory loadBalancerClientFactory(LoadBalancerClientsProperties properties,
//                                                               ObjectProvider<List<LoadBalancerClientSpecification>> configurations) {
//        EnvLoadBalancerClientFactory clientFactory = new EnvLoadBalancerClientFactory(properties);
//        clientFactory.setConfigurations(configurations.getIfAvailable(Collections::emptyList));
//        return clientFactory;
//    }

    @Bean
    public EnvRequestInterceptor envRequestInterceptor() {
        return new EnvRequestInterceptor();
    }

    // ========== Dubbo 相关 ==========

}
