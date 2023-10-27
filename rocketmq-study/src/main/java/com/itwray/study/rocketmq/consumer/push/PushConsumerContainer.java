package com.itwray.study.rocketmq.consumer.push;

import com.alibaba.fastjson.JSON;
import com.itwray.study.rocketmq.consumer.ConsumerBusinessException;
import com.itwray.study.rocketmq.consumer.ConsumerMethod;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * Push消费模式的消费者容器
 *
 * @author Wray
 * @since 2023/10/27
 */
public class PushConsumerContainer implements InitializingBean, DisposableBean {

    private final ConsumerMethod consumerMethod;

    private final Logger log = LoggerFactory.getLogger(PushConsumerContainer.class);

    private MQPushConsumer pushConsumer;

    public PushConsumerContainer(ConsumerMethod consumerMethod) {
        this.consumerMethod = consumerMethod;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pushConsumer.start();
    }

    public void setPushConsumer(MQPushConsumer pushConsumer) {
        this.pushConsumer = pushConsumer;
        this.pushConsumer.registerMessageListener(new DefaultMessageLister());
    }

    public void startUp() {
        if (this.pushConsumer == null) {
            throw new NullPointerException("Consumer are not configured!");
        }
    }

    @Override
    public void destroy() {
        pushConsumer.shutdown();
    }

    private Object convertMessage(MessageExt messageExt, Class<?> messageType) {
        if (Objects.equals(messageType, MessageExt.class)) {
            return messageExt;
        } else {
            String str = new String(messageExt.getBody(), StandardCharsets.UTF_8);
            if (Objects.equals(messageType, String.class)) {
                return str;
            } else {
                return JSON.parseObject(str, messageType);
            }
        }
    }

    public class DefaultMessageLister implements MessageListenerOrderly {

        @Override
        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeOrderlyContext context) {
            for (MessageExt messageExt : messageExtList) {
                Object message = convertMessage(messageExt, consumerMethod.getParamClazz());
                System.out.println("Push消费消息: " + messageExt.getQueueId() + "&" + messageExt.getQueueOffset() + ", 消息内容为: " + message);
                try {
                    consumerMethod.invoke(message);
                } catch (ConsumerBusinessException e) {
                    System.out.println("Push回溯的队列偏移量: " + messageExt.getQueueId() + "&" + messageExt.getQueueOffset() + ", 消息内容为: " + message);
                    log.error("Push消费消息异常", e);
                    context.setSuspendCurrentQueueTimeMillis(1000L);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                } finally {
                    // 调试换行
                    System.out.println();
                }
            }
            return ConsumeOrderlyStatus.SUCCESS;
        }
    }

}
