package com.itwray.study.rocketmq.consumer;

import org.apache.rocketmq.spring.annotation.ExtRocketMQConsumerConfiguration;
import org.apache.rocketmq.spring.annotation.ExtRocketMQTemplateConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@ExtRocketMQConsumerConfiguration(topic = "${rocketmq.demo.topic}")
@ExtRocketMQTemplateConfiguration
public class ExtRocketMQTemplate extends RocketMQTemplate {

    @PostConstruct
    public void init() {
        this.setCharset(StandardCharsets.UTF_8.name());
    }
}