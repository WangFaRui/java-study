package com.itwray.study.rocketmq.consumer.ext;

import org.apache.rocketmq.spring.annotation.ExtRocketMQConsumerConfiguration;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

@ExtRocketMQConsumerConfiguration(messageModel = MessageModel.CLUSTERING)
public class ExtRocketMQTemplate2 extends RocketMQTemplate {

}