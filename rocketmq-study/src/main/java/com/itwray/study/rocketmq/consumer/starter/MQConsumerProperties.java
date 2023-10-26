package com.itwray.study.rocketmq.consumer.starter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MQ消费者属性
 *
 * @author Wray
 * @since 2023/10/26
 */
@ConfigurationProperties("rocketmq.consumer")
public class MQConsumerProperties {

    @Value("${rocketmq.name-server}")
    private String nameServer;

    private String topic;

    private String group;

    public String getNameServer() {
        return nameServer;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
