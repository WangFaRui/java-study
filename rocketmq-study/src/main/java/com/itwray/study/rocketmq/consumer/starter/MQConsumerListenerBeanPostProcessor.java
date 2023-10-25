package com.itwray.study.rocketmq.consumer.starter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 消息消费者监听器的后置处理器
 *
 * @author Wray
 * @since 2023/10/25
 */
public class MQConsumerListenerBeanPostProcessor implements BeanPostProcessor {

    private LitePullConsumerConfiguration litePullConsumerConfiguration;

    @Autowired
    public void setListPullConsumerConfiguration(LitePullConsumerConfiguration litePullConsumerConfiguration) {
        this.litePullConsumerConfiguration = litePullConsumerConfiguration;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithMethods(bean.getClass(), method -> {
            litePullConsumerConfiguration.registerConsumer(method, bean, beanName);
        }, MqConsumeListenerMethodFilter.DEFAULT_FILTER);

        return bean;
    }

    public static class MqConsumeListenerMethodFilter implements ReflectionUtils.MethodFilter {

        public static MqConsumeListenerMethodFilter DEFAULT_FILTER;

        static {
            DEFAULT_FILTER = new MqConsumeListenerMethodFilter();
        }

        @Override
        public boolean matches(Method method) {
            MQConsumerListener annotation = AnnotationUtils.findAnnotation(method, MQConsumerListener.class);
            return annotation != null;
        }
    }
}
