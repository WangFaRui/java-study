package com.itwray.study.rocketmq.producer;

import org.springframework.stereotype.Service;

/**
 * 消息生产服务
 *
 * @author Wray
 * @since 2023/10/20
 */
@Service
public class ProducerService {

    public void send(String topic, Object msg) {
        int hashCode = msg.hashCode();
        // 0 or 1
        String tag = "tag_" + hashCode % 2;
        ProducerHelper.send(msg, topic, tag);
    }

    public void send(String msg, Integer size) {
        for (int i=1; i<=size; i++) {
            String actualMsg = msg + i;
            this.send(null, actualMsg);
        }
    }
}
