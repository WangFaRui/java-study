package com.itwray.study.rocketmq.consumer.starter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * RocketMQ消费者自动装配类
 *
 * @author Wray
 * @since 2023/10/25
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MQConsumerProperties.class)
@Import({LitePullConsumerConfiguration.class, MQConsumerListenerBeanPostProcessor.class})
public class MQConsumerAutoConfiguration {
}
