package com.itwray.study.rocketmq.producer;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 消息生产服务
 *
 * @author Wray
 * @since 2023/10/20
 */
@Service
public class ProducerService {

    @Resource
    private MQProducer mqProducer;

    @Value("${rocketmq.demo.topic}")
    private String defaultTopic;

    public void send(String topic, String msg) {
        Message message = new Message();
        message.setBody(msg.getBytes());
        message.setTopic(StringUtils.isBlank(topic) ? this.defaultTopic : topic);
        int hashCode = msg.hashCode();
        // 0 or 1
        String tag = "tag_" + hashCode % 2;
        message.setTags(tag);
        try {
            SendResult result = mqProducer.send(message);
            System.out.println("mqProducer send result: " + result);
            System.out.println("message tag: " + tag);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String msg, Integer size) {
        for (int i=1; i<=size; i++) {
            String actualMsg = msg + i;
            this.send(this.defaultTopic, actualMsg);
        }
    }
}
