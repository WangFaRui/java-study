package com.itwray.study.rocketmq.consumer;

import java.lang.reflect.Method;

/**
 * 消费者配置类
 *
 * @author Wray
 * @since 2023/10/27
 */
public interface ConsumerConfigurator {

    void registerConsumer(Method method, Object bean, String beanName);
}
