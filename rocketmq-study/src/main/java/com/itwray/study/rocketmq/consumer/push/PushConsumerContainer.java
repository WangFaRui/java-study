package com.itwray.study.rocketmq.consumer.push;

import com.itwray.study.rocketmq.consumer.AbstractConsumerContainer;
import com.itwray.study.rocketmq.consumer.ConsumerBusinessException;
import com.itwray.study.rocketmq.consumer.ConsumerMethod;
import com.itwray.study.rocketmq.consumer.MessageListeningRule;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Push消费模式的消费者容器
 *
 * @author Wray
 * @since 2023/10/27
 */
public class PushConsumerContainer extends AbstractConsumerContainer {

    private final Logger log = LoggerFactory.getLogger(PushConsumerContainer.class);

    private MQPushConsumer pushConsumer;

    public PushConsumerContainer(ConsumerMethod consumerMethod) {
        super(consumerMethod);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pushConsumer.start();
    }

    @Override
    public void startUp() {
        if (this.pushConsumer == null) {
            throw new NullPointerException("Consumer are not configured!");
        }
    }

    @Override
    public void destroy() {
        pushConsumer.shutdown();
    }

    public void setPushConsumer(MQPushConsumer pushConsumer, MessageListeningRule messageListeningRule) {
        this.pushConsumer = pushConsumer;
        switch (messageListeningRule) {
            case ORDERLY:
                this.pushConsumer.registerMessageListener(new OrderlyMessageLister());
                break;
            case CONCURRENTLY:
                this.pushConsumer.registerMessageListener(new ConcurrentlyMessageListener());
                break;
            default:
                throw new IllegalArgumentException("messageListeningRule is illegal argument");
        }
    }

    public class OrderlyMessageLister implements MessageListenerOrderly {

        @Override
        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeOrderlyContext context) {
            for (MessageExt messageExt : messageExtList) {
                Object message = convertMessage(messageExt, getConsumerMethod().getParamClazz());
                System.out.println("Push Orderly消费消息: " + messageExt.getQueueId() + "&" + messageExt.getQueueOffset() + ", 消息内容为: " + message);
                try {
                    getConsumerMethod().invoke(message);
                } catch (ConsumerBusinessException e) {
                    System.out.println("Push Orderly回溯的队列偏移量: " + messageExt.getQueueId() + "&" + messageExt.getQueueOffset() + ", 消息内容为: " + message);
                    log.error("Push Orderly消费消息异常", e);
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

    public class ConcurrentlyMessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext context) {
            for (MessageExt messageExt : messageExtList) {
                Object message = convertMessage(messageExt, getConsumerMethod().getParamClazz());
                System.out.println("Push Concurrently消费消息: " + messageExt.getQueueId() + "&" + messageExt.getQueueOffset() + ", 消息内容为: " + message);
                try {
                    getConsumerMethod().invoke(message);
                } catch (ConsumerBusinessException e) {
                    System.out.println("Push Concurrently回溯的队列偏移量: " + messageExt.getQueueId() + "&" + messageExt.getQueueOffset() + ", 消息内容为: " + message);
                    log.error("Push Concurrently消费消息异常", e);
                    context.setDelayLevelWhenNextConsume(0);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                } finally {
                    // 调试换行
                    System.out.println();
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

}
