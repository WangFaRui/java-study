package com.itwray.study.rocketmq.consumer;

/**
 * 消息监听规则
 * <p>适用于Push消费模式</p>
 *
 * @author Wray
 * @since 2023/10/30
 */
public enum MessageListeningRule {
    ORDERLY,
    CONCURRENTLY
}
