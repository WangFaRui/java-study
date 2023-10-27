package com.itwray.study.rocketmq.consumer;

import com.itwray.study.rocketmq.consumer.pull.LitePullConsumerConfigurator;
import com.itwray.study.rocketmq.consumer.push.PushConsumerConfigurator;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 消费者配置工厂
 *
 * @author Wray
 * @since 2023/10/27
 */
public class ConsumerConfiguratorFactory {

    private static final Map<ConsumeListeningMode, ConsumerConfigurator> consumerConfiguratorMap = new HashMap<>(8);

    public synchronized static ConsumerConfigurator getConsumerConfigurator(ConsumeListeningMode mode,
                                                                            ApplicationContext applicationContext) {
        ConsumerConfigurator consumerConfigurator = consumerConfiguratorMap.get(mode);
        if (consumerConfigurator != null) {
            return consumerConfigurator;
        }
        switch (mode) {
            case LITE_PULL:
                consumerConfigurator = applicationContext.getBean(LitePullConsumerConfigurator.class);
                break;
            case PUSH:
                consumerConfigurator = applicationContext.getBean(PushConsumerConfigurator.class);
                break;
            default:
                throw new IllegalArgumentException("unknown consume listening mode");
        }
        consumerConfiguratorMap.put(mode, consumerConfigurator);
        return consumerConfigurator;
    }
}
