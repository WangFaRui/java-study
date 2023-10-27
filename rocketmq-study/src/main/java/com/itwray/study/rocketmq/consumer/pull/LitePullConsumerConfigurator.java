package com.itwray.study.rocketmq.consumer.pull;

import com.itwray.study.rocketmq.consumer.*;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.topic.TopicValidator;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * Lite Pull消费模式的消费者配置器
 *
 * @author Wray
 * @since 2023/10/25
 */
public class LitePullConsumerConfigurator extends AbstractConsumerConfigurator implements ConsumerConfigurator {

    private final Logger log = LoggerFactory.getLogger(LitePullConsumerConfigurator.class);

    public LitePullConsumerConfigurator(MQConsumerProperties mqConsumerProperties) {
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
        this.getGenericApplicationContext().registerBean(containerBeanName, LitePullConsumerContainer.class,
                () -> createLitePullConsumerContainer(consumerMethod, annotation));
        LitePullConsumerContainer container = this.getGenericApplicationContext().getBean(containerBeanName,
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
        MQConsumerProperties mqConsumerProperties = this.getMqConsumerProperties();
        String nameServer = this.resolvePlaceholders(annotation.nameServer(), mqConsumerProperties.getNameServer());
        String groupName = this.resolvePlaceholders(annotation.group(), mqConsumerProperties.getGroup());
        String topicName = this.resolvePlaceholders(annotation.topic(), mqConsumerProperties.getTopic());
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
}
