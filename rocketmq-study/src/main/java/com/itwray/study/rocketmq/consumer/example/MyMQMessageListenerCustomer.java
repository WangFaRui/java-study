package com.itwray.study.rocketmq.consumer.example;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * 基于{@link RocketMQMessageListener}实现的消费者
 *
 * @author Wray
 * @since 2023/11/15
 */
@RocketMQMessageListener(consumerGroup = "listener_group", topic = "TestTopic")
@Service
public class MyMQMessageListenerCustomer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("MyMQMessageListenerCustomer: " + message);
    }
}
