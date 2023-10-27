package com.itwray.study.rocketmq.consumer.push;

import com.itwray.study.rocketmq.consumer.*;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * Push消费模式的消费者配置器
 *
 * @author Wray
 * @since 2023/10/27
 */
public class PushConsumerConfigurator extends AbstractConsumerConfigurator implements ConsumerConfigurator {

    private final Logger log = LoggerFactory.getLogger(PushConsumerConfigurator.class);

    public PushConsumerConfigurator(MQConsumerProperties mqConsumerProperties) {
        super(mqConsumerProperties);
    }

    @Override
    public void registerConsumer(Method method, Object bean, String beanName) {
        MQConsumerListener annotation = AnnotationUtils.getAnnotation(method, MQConsumerListener.class);
        if (annotation == null) {
            log.warn("method without MQConsumerListener");
            return;
        }
        String containerBeanName = StringUtils.hasText(annotation.value()) ? annotation.value() : beanName + "$" + method.getName();
        ConsumerMethod consumerMethod = new ConsumerMethod(method, bean);
        this.getGenericApplicationContext().registerBean(containerBeanName, PushConsumerContainer.class,
                () -> createPushConsumerContainer(consumerMethod, annotation));
        PushConsumerContainer container = this.getGenericApplicationContext().getBean(containerBeanName,
                PushConsumerContainer.class);
        container.startUp();
    }

    private PushConsumerContainer createPushConsumerContainer(ConsumerMethod consumerMethod,
                                                              MQConsumerListener annotation) {
        PushConsumerContainer container = new PushConsumerContainer(consumerMethod);
        try {
            DefaultMQPushConsumer pushConsumer = this.createConsumer(annotation);
            container.setPushConsumer(pushConsumer);
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
        return container;
    }

    private DefaultMQPushConsumer createConsumer(MQConsumerListener annotation) throws MQClientException {
        MQConsumerProperties mqConsumerProperties = this.getMqConsumerProperties();
        String nameServer = this.resolvePlaceholders(annotation.nameServer(), mqConsumerProperties.getNameServer());
        String groupName = this.resolvePlaceholders(annotation.group(), mqConsumerProperties.getGroup());
        String topicName = this.resolvePlaceholders(annotation.topic(), mqConsumerProperties.getTopic());
        MessageModel messageModel = annotation.messageModel();
        SelectorType selectorType = annotation.selectorType();
        String selectorExpression = annotation.selectorExpression();

        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(groupName);
        pushConsumer.setNamesrvAddr(nameServer);

        switch (messageModel) {
            case BROADCASTING:
                pushConsumer.setMessageModel(org.apache.rocketmq.common.protocol.heartbeat.MessageModel.BROADCASTING);
                break;
            case CLUSTERING:
                pushConsumer.setMessageModel(org.apache.rocketmq.common.protocol.heartbeat.MessageModel.CLUSTERING);
                break;
            default:
                throw new IllegalArgumentException("Property 'messageModel' was wrong.");
        }

        switch (selectorType) {
            case TAG:
                pushConsumer.subscribe(topicName, selectorExpression);
                break;
            case SQL92:
                pushConsumer.subscribe(topicName, MessageSelector.bySql(selectorExpression));
                break;
            default:
                throw new IllegalArgumentException("Property 'selectorType' was wrong.");
        }

        return pushConsumer;
    }


}
