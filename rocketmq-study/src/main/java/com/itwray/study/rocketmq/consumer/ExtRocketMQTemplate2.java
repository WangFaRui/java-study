package com.itwray.study.rocketmq.consumer;

import org.apache.rocketmq.spring.annotation.ExtRocketMQConsumerConfiguration;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

@ExtRocketMQConsumerConfiguration(topic = "${rocketmq.demo.topic}", messageModel = MessageModel.BROADCASTING)
public class ExtRocketMQTemplate2 extends RocketMQTemplate {

}