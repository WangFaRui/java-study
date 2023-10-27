package com.itwray.study.rocketmq.consumer.pull;

import com.itwray.study.rocketmq.consumer.ConsumerMethod;
import com.itwray.study.rocketmq.consumer.MQConsumerListener;
import com.itwray.study.rocketmq.consumer.MQConsumerProperties;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.topic.TopicValidator;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 消费者配置类
 *
 * @author Wray
 * @since 2023/10/25
 */
@Configuration(proxyBeanMethods = false)
public class LitePullConsumerConfiguration implements ApplicationContextAware, EnvironmentAware {

    private GenericApplicationContext genericApplicationContext;

    private Environment environment;

    private final Logger log = LoggerFactory.getLogger(LitePullConsumerConfiguration.class);

    private final MQConsumerProperties mqConsumerProperties;

    public LitePullConsumerConfiguration(MQConsumerProperties mqConsumerProperties) {
        this.mqConsumerProperties = mqConsumerProperties;
    }

    public void registerConsumer(Method method, Object bean, String beanName) {
        MQConsumerListener annotation = AnnotationUtils.getAnnotation(method, MQConsumerListener.class);
        if (annotation == null) {
            log.warn("method without MQConsumerListener");
            return;
        }
        ConsumerMethod consumerMethod = new ConsumerMethod(method, bean);
        String containerBeanName = StringUtils.hasText(annotation.value()) ? annotation.value() : beanName + "$" + method.getName();
        this.genericApplicationContext.registerBean(containerBeanName, LitePullConsumerContainer.class,
                () -> createLitePullConsumerContainer(consumerMethod, annotation));
        LitePullConsumerContainer container = this.genericApplicationContext.getBean(containerBeanName,
                LitePullConsumerContainer.class);
        container.startUp();
    }

    private LitePullConsumerContainer createLitePullConsumerContainer(ConsumerMethod consumerMethod,
                                                                      MQConsumerListener annotation) {
        LitePullConsumerContainer container = new LitePullConsumerContainer(consumerMethod);
        try {
            DefaultLitePullConsumer litePullConsumer = this.createConsumer(annotation);
            container.setLitePullConsumer(litePullConsumer);
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
        return container;
    }

    private DefaultLitePullConsumer createConsumer(MQConsumerListener annotation) throws MQClientException {
        String nameServer = this.resolvePlaceholders(annotation.nameServer(), this.mqConsumerProperties.getNameServer());
        String groupName = this.resolvePlaceholders(annotation.group(), this.mqConsumerProperties.getGroup());
        String topicName = this.resolvePlaceholders(annotation.topic(), this.mqConsumerProperties.getTopic());
        MessageModel messageModel = annotation.messageModel();
        SelectorType selectorType = annotation.selectorType();
        String selectorExpression = annotation.selectorExpression();

        String accessChannel = null;
        String ak = null;
        String sk = null;
        int pullBatchSize = 10;
        boolean useTLS = false;

        DefaultLitePullConsumer litePullConsumer = RocketMQUtil.createDefaultLitePullConsumer(nameServer, accessChannel,
                groupName, topicName, messageModel, selectorType, selectorExpression, ak, sk, pullBatchSize, useTLS);
        litePullConsumer.setEnableMsgTrace(false);
        litePullConsumer.setCustomizedTraceTopic(TopicValidator.RMQ_SYS_TRACE_TOPIC);
        litePullConsumer.setNamespace(null);
        litePullConsumer.setInstanceName("DEFAULT");
        return litePullConsumer;
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

    private String resolvePlaceholders(String text, String defaultValue) {
        String value = this.environment.resolvePlaceholders(text);
        return StringUtils.hasText(value) ? value : defaultValue;
    }
}
