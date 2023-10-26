package com.itwray.study.rocketmq.consumer.starter;

import com.alibaba.fastjson.JSON;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 消费者方法
 *
 * @author Wray
 * @since 2023/10/25
 */
public class ConsumerMethod {

    private final Method method;

    private final Object target;

    private final Class<?> paramClazz;

    public ConsumerMethod(Method method, Object target) {
        this.method = method;
        this.target = target;
        Class<?>[] parameterTypes = method.getParameterTypes();
        this.paramClazz = parameterTypes[0];
    }

    public void invoke(Object param) {
        try {
            ReflectionUtils.invokeMethod(this.method, this.target, param);
        } catch (Exception e) {
            String sb = "MQ消费者(" +
                    this.target.getClass().getName() +
                    "#" +
                    this.method.getName() +
                    ")消费异常, 消费参数: " +
                    JSON.toJSONString(param);
            throw new ConsumerBusinessException(sb, e);
        }
    }

    public Class<?> getParamClazz() {
        return paramClazz;
    }
}
