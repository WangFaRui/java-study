package com.itwray.study.rocketmq.consumer;

import com.itwray.study.rocketmq.consumer.starter.MQConsumerListener;
import org.apache.rocketmq.client.consumer.LitePullConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息消费服务
 *
 * @author Wray
 * @since 2023/10/24
 */
@Service
public class ConsumerService {

    @Resource
    private LitePullConsumer pullConsumer;

    @Resource
    private ExtRocketMQTemplate extRocketMQTemplate;

    @Resource
    private ExtRocketMQTemplate2 extRocketMQTemplate2;

    public List<String> receiveMessage() {
        List<MessageExt> messageExtList = pullConsumer.poll();
        System.out.println("收到" + messageExtList.size() + "条消息");
        List<String> result = new ArrayList<>(messageExtList.size());
        for (MessageExt messageExt : messageExtList) {
            String msg = new String(messageExt.getBody(), StandardCharsets.UTF_8);
            System.out.println("Consumer message: " + msg);
            result.add(msg);
        }
        return result;
    }

    public List<String> extConsume() {
        List<String> messageExtList = extRocketMQTemplate.receive(String.class);
        System.out.println("扩展收到" + messageExtList.size() + "条消息");
        messageExtList.forEach(msg -> System.out.println("Consumer message: " + msg));
        return messageExtList;
    }

    public List<String> extConsume2() {
        List<String> messageExtList = extRocketMQTemplate2.receive(String.class);
        System.out.println("扩展2收到" + messageExtList.size() + "条消息");
        messageExtList.forEach(msg -> System.out.println("Consumer2 message: " + msg));
        return messageExtList;
    }

    @MQConsumerListener()
    public void consumeListener(String msg) {
        System.out.printf("消费者监听[%s]: %s\n", Thread.currentThread().getName(), msg);
    }
}
