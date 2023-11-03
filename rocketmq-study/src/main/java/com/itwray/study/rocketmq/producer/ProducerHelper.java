package com.itwray.study.rocketmq.producer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 生产者助手
 *
 * @author Wray
 * @since 2023/10/31
 */
@Component
public class ProducerHelper {

    private static final Logger log = LoggerFactory.getLogger(ProducerHelper.class);

    private static MQProducer mqProducer;

    private static String defaultTopic;

    public ProducerHelper(@Autowired MQProducer mqProducer, @Value("${rocketmq.demo.topic}") String topic) {
        ProducerHelper.mqProducer = mqProducer;
        ProducerHelper.defaultTopic = topic;
    }

    public static String send(Object message) {
        Message msg = buildMessage(message, null, null, null);
        return doSend(msg);
    }

    public static String send(Object message, String topic) {
        Message msg = buildMessage(message, topic, null, null);
        return doSend(msg);
    }

    public static String send(Object message, String topic, String tags) {
        Message msg = buildMessage(message, topic, tags, null);
        return doSend(msg);
    }

    public static String send(Object message, String topic, String tags, String keys) {
        Message msg = buildMessage(message, topic, tags, keys);
        return doSend(msg);
    }

    public static Message buildMessage(Object message, String topic, String tags, String keys) {
        topic = StringUtils.hasText(topic) ? topic : defaultTopic;
        byte[] body = JSON.toJSONBytes(message);
        return new Message(topic, tags, keys, body);
    }

    public static String doSend(Message msg) {
        try {
            SendResult result = mqProducer.send(msg);
            log.info("MQ消息发送成功，消息结果: " + result);
            return result.getMsgId();
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            log.error("MQ消息发送异常, 消息内容: " + msg, e);
            throw new RuntimeException(e);
        }
    }
}
