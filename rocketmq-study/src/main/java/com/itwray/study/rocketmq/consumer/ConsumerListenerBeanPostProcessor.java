package com.itwray.study.rocketmq.consumer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

/**
 * 消息消费者监听器的后置处理器
 *
 * @author Wray
 * @since 2023/10/25
 */
public class ConsumerListenerBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithMethods(bean.getClass(), method -> {
            MQConsumerListener annotation = AnnotationUtils.getAnnotation(method, MQConsumerListener.class);
            if (annotation != null) {
                ConsumerConfigurator consumerConfigurator = ConsumerConfiguratorFactory.getConsumerConfigurator(
                        annotation.consumeListeningMode(), this.applicationContext
                );
                consumerConfigurator.registerConsumer(method, bean, beanName);
            }
        });

        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
