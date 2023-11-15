package com.itwray.study.rocketmq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;

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

    @Nullable
    public Object convertMessage(MessageExt messageExt, Class<?> messageType) {
        if (Objects.equals(messageType, MessageExt.class)) {
            return messageExt;
        } else {
            if (Objects.equals(messageType, String.class)) {
                return new String(messageExt.getBody(), StandardCharsets.UTF_8);
            } else {
                try {
                    return JSON.parseObject(messageExt.getBody(), messageType);
                } catch (JSONException e) {
                    System.err.println("消息类型转换异常, 消息内容: " + new String(messageExt.getBody(), StandardCharsets.UTF_8));
                    return null;
                }
            }
        }
    }

}
