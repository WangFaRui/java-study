package com.itwray.study.rocketmq.consumer.pull;

import com.alibaba.fastjson.JSON;
import com.itwray.study.rocketmq.consumer.ConsumerBusinessException;
import com.itwray.study.rocketmq.consumer.ConsumerMethod;
import com.itwray.study.rocketmq.consumer.ConsumerThreadFactoryImpl;
import org.apache.rocketmq.client.consumer.LitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Lite Pull消费模式的消费者容器
 *
 * @author Wray
 * @since 2023/10/25
 */
public class LitePullConsumerContainer implements InitializingBean, DisposableBean {

    private final ScheduledExecutorService executorService;

    private final ConsumerMethod consumerMethod;

    private final Logger log = LoggerFactory.getLogger(LitePullConsumerContainer.class);

    private LitePullConsumer litePullConsumer;

    public LitePullConsumerContainer(ConsumerMethod consumerMethod) {
        this.consumerMethod = consumerMethod;

        executorService = new ScheduledThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                new ConsumerThreadFactoryImpl("LitePullConsumer")
        );
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!litePullConsumer.isRunning()) {
            litePullConsumer.start();
        }
    }

    public void setLitePullConsumer(LitePullConsumer litePullConsumer) {
        this.litePullConsumer = litePullConsumer;
    }

    public void startUp() {
        if (this.litePullConsumer == null) {
            throw new NullPointerException("Consumer are not configured!");
        }

        executorService.scheduleWithFixedDelay(this::run, 1, 3, TimeUnit.SECONDS);
    }

    @Override
    public void destroy() {
        executorService.shutdown();
        litePullConsumer.shutdown();
    }

    private void run() {
        List<MessageExt> messageExtList = this.litePullConsumer.poll();
        for (MessageExt messageExt : messageExtList) {
            Object message = this.convertMessage(messageExt, this.consumerMethod.getParamClazz());
            System.out.println("Lite Pull消费消息: " + messageExt.getQueueId() + "&" + messageExt.getQueueOffset()+ ", 消息内容为: " + message);
            try {
                this.consumerMethod.invoke(message);
            } catch (ConsumerBusinessException e) {
                try {
                    System.out.println("Lite Pull回溯的队列偏移量: " + messageExt.getQueueId() + "&" + messageExt.getQueueOffset() + ", 消息内容为: " + message);
                    log.error("Lite Pull消费消息异常", e);
                    this.litePullConsumer.seek(this.buildMessageQueue(messageExt), messageExt.getQueueOffset());
                } catch (MQClientException ex) {
                    log.error(ex.getMessage(), ex);
                }
            } finally {
                // 调试换行
                System.out.println();
            }
        }
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

    private MessageQueue buildMessageQueue(MessageExt messageExt) {
        return new MessageQueue(messageExt.getTopic(), messageExt.getBrokerName(), messageExt.getQueueId());
    }
}
