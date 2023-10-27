package com.itwray.study.rocketmq.consumer.pull;

import com.itwray.study.rocketmq.consumer.MQConsumerProperties;
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
@Import({LitePullConsumerConfiguration.class, LiteConsumerListenerBeanPostProcessor.class})
public class LiteConsumerAutoConfiguration {
}
