package com.itwray.study.rocketmq.consumer.starter;

/**
 * 消费者业务异常
 *
 * @author Wray
 * @since 2023/10/26
 */
public class ConsumerBusinessException extends RuntimeException {

    public ConsumerBusinessException() {
        super();
    }

    public ConsumerBusinessException(String message) {
        super(message);
    }

    public ConsumerBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
