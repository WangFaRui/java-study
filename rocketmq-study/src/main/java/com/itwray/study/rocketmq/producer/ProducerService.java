package com.itwray.study.rocketmq.producer;

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

    @Value("${rocketmq.topic}")
    private String topic;

    public void send(String msg) {
        Message message = new Message();
        message.setBody(msg.getBytes());
        message.setTopic(topic);
        try {
            SendResult result = mqProducer.send(message);
            System.out.println("mqProducer send result: " + result);
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        } catch (RemotingException e) {
            throw new RuntimeException(e);
        } catch (MQBrokerException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
