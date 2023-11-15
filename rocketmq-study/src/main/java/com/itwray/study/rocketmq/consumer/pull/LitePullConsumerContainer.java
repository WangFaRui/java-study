package com.itwray.study.rocketmq.consumer.pull;

import com.itwray.study.rocketmq.consumer.AbstractConsumerContainer;
import com.itwray.study.rocketmq.consumer.ConsumerBusinessException;
import com.itwray.study.rocketmq.consumer.ConsumerMethod;
import com.itwray.study.rocketmq.consumer.ConsumerThreadFactoryImpl;
import org.apache.rocketmq.client.consumer.LitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Lite Pull消费模式的消费者容器
 *
 * @author Wray
 * @since 2023/10/25
 */
public class LitePullConsumerContainer extends AbstractConsumerContainer {

    private final ScheduledExecutorService executorService;

    private final Logger log = LoggerFactory.getLogger(LitePullConsumerContainer.class);

    private LitePullConsumer litePullConsumer;

    public LitePullConsumerContainer(ConsumerMethod consumerMethod) {
        super(consumerMethod);

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

    @Override
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

    public void setLitePullConsumer(LitePullConsumer litePullConsumer) {
        this.litePullConsumer = litePullConsumer;
    }

    private void run() {
        List<MessageExt> messageExtList = this.litePullConsumer.poll();
        for (MessageExt messageExt : messageExtList) {
            Object message = this.convertMessage(messageExt, this.getConsumerMethod().getParamClazz());
            System.out.println("Lite Pull消费消息: " + messageExt.getQueueId() + "&" + messageExt.getQueueOffset()+ ", 消息内容为: " + message);
            if (message == null) {
                continue;
            }
            try {
                this.getConsumerMethod().invoke(message);
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

    private MessageQueue buildMessageQueue(MessageExt messageExt) {
        return new MessageQueue(messageExt.getTopic(), messageExt.getBrokerName(), messageExt.getQueueId());
    }
}
