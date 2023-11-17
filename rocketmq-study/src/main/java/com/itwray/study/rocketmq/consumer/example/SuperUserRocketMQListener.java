package com.itwray.study.rocketmq.consumer.example;

import com.itwray.study.rocketmq.domain.SuperUser;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * 基于{@link SuperUser}的RocketMQListener
 *
 * @author Wray
 * @since 2023/11/16
 */
public abstract class SuperUserRocketMQListener<R extends SuperUser> implements RocketMQListener<R> {
}
