package com.itwray.study.rocketmq.consumer.example;

import com.itwray.study.rocketmq.domain.UserVo;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * 基于{@link RocketMQMessageListener}实现的消费者
 *
 * @author Wray
 * @since 2023/11/15
 */
//@Service
@RocketMQMessageListener(consumerGroup = "listener_group", topic = "TestTopic")
public class UserVoMQMessageListener extends SuperUserRocketMQListener<UserVo> implements RocketMQListener<UserVo> {

    @Override
    public void onMessage(UserVo message) {
        System.out.println("UserVoMQMessageListener: " + message);
    }
}
