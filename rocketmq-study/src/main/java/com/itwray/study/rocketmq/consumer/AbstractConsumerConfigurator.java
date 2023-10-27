package com.itwray.study.rocketmq.consumer;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * 消费者配置器抽象类
 *
 * @author Wray
 * @since 2023/10/27
 */
public abstract class AbstractConsumerConfigurator implements ConsumerConfigurator, ApplicationContextAware, EnvironmentAware {

    private GenericApplicationContext genericApplicationContext;

    private Environment environment;

    private final MQConsumerProperties mqConsumerProperties;

    public AbstractConsumerConfigurator(MQConsumerProperties mqConsumerProperties) {
        this.mqConsumerProperties = mqConsumerProperties;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof GenericApplicationContext) {
            this.genericApplicationContext = (GenericApplicationContext) applicationContext;
        } else {
            throw new IllegalArgumentException("ApplicationContext is not GenericApplicationContext!");
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String resolvePlaceholders(String text, String defaultValue) {
        String value = this.environment.resolvePlaceholders(text);
        return StringUtils.hasText(value) ? value : defaultValue;
    }

    public GenericApplicationContext getGenericApplicationContext() {
        return this.genericApplicationContext;
    }

    public MQConsumerProperties getMqConsumerProperties() {
        return this.mqConsumerProperties;
    }
}
