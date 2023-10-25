package com.itwray.study.rocketmq.consumer.starter;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.LitePullConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 消费者容器
 *
 * @author Wray
 * @since 2023/10/25
 */
public class LitePullConsumerContainer implements InitializingBean, DisposableBean {

    private LitePullConsumer litePullConsumer;

    private final ScheduledExecutorService executorService;

    private final ConsumerMethod consumerMethod;

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
            throw new IllegalStateException("Consumer are not configured!");
        }

        executorService.scheduleWithFixedDelay(() -> {
            List<MessageExt> messageExtList = this.litePullConsumer.poll();
            for (MessageExt messageExt : messageExtList) {
                String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
                Object object = JSON.parseObject(message, this.consumerMethod.getParamClazz());
                consumerMethod.invoke(object);
            }
        }, 1, 5, TimeUnit.SECONDS);
    }

    @Override
    public void destroy() {
        executorService.shutdown();
        litePullConsumer.shutdown();
    }

}
