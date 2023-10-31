package com.itwray.study.rocketmq.consumer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 消费者容器抽象类
 *
 * @author Wray
 * @since 2023/10/28
 */
public abstract class AbstractConsumerContainer implements ConsumerContainer, InitializingBean, DisposableBean {

    private final ConsumerMethod consumerMethod;

    public AbstractConsumerContainer(ConsumerMethod consumerMethod) {
        this.consumerMethod = consumerMethod;
    }

    public ConsumerMethod getConsumerMethod() {
        return consumerMethod;
    }

    public Object convertMessage(MessageExt messageExt, Class<?> messageType) {
        if (Objects.equals(messageType, MessageExt.class)) {
            return messageExt;
        } else {
            if (Objects.equals(messageType, String.class)) {
                return new String(messageExt.getBody(), StandardCharsets.UTF_8);
            } else {
                return JSON.parseObject(messageExt.getBody(), messageType);
            }
        }
    }

}
