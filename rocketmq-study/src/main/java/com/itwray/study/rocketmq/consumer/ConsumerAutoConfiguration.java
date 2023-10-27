package com.itwray.study.rocketmq.consumer;

import com.itwray.study.rocketmq.consumer.pull.LitePullConsumerConfigurator;
import com.itwray.study.rocketmq.consumer.push.PushConsumerConfigurator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 消费配置类
 *
 * @author Wray
 * @since 2023/10/27
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MQConsumerProperties.class)
@Import(ConsumerListenerBeanPostProcessor.class)
public class ConsumerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LitePullConsumerConfigurator litePullConsumerConfigurator(MQConsumerProperties mqConsumerProperties) {
        return new LitePullConsumerConfigurator(mqConsumerProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public PushConsumerConfigurator pushConsumerConfigurator(MQConsumerProperties mqConsumerProperties) {
        return new PushConsumerConfigurator(mqConsumerProperties);
    }
}
