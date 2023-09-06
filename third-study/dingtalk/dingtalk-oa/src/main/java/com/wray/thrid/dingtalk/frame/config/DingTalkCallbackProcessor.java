package com.wray.thrid.dingtalk.frame.config;

import com.google.common.base.Preconditions;
import com.wray.thrid.dingtalk.frame.annotation.ApprovalCallback;
import com.wray.thrid.dingtalk.frame.listener.DingTalkCallbackListener;
import com.wray.thrid.dingtalk.frame.model.DingTalkCallbackEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * 钉钉回调的后置处理器
 *
 * @author wangfarui
 * @since 2023/8/16
 */
public class DingTalkCallbackProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        // Bean初始化前, 扫描Bean的所有方法
        for (Method method : findAllMethod(clazz)) {
            processMethod(bean, method);
        }
        return bean;
    }

    private void processMethod(Object bean, Method method) {
        ApprovalCallback annotation = AnnotationUtils.findAnnotation(method, ApprovalCallback.class);
        if (annotation == null) {
            return;
        }

        // 获取方法的入参，要求回调方法的入参个数有切仅有一个，并且入参对象继承自DingTalkCallbackEvent
        Class<?>[] parameterTypes = method.getParameterTypes();
        Preconditions.checkArgument(parameterTypes.length == 1,
                "Invalid number of parameters: %s for method: %s, should be 1", parameterTypes.length,
                method);
        Preconditions.checkArgument(DingTalkCallbackEvent.class.isAssignableFrom(parameterTypes[0]),
                "Invalid parameter type: %s for method: %s, should be DingTalkCallbackEvent", parameterTypes[0],
                method);

        ReflectionUtils.makeAccessible(method);

        // 创建一个钉钉回调监听器，回调实现方法默认调用当前bean对象的方法
        DingTalkCallbackListener dingTalkCallbackListener = (callbackEvent) ->
                ReflectionUtils.invokeMethod(method, bean, callbackEvent);

        // 将监听器添加到容器
        DingTalkConfig.addCallbackListener(annotation, dingTalkCallbackListener);
    }

    private List<Method> findAllMethod(Class<?> clazz) {
        final List<Method> res = new LinkedList<>();
        ReflectionUtils.doWithMethods(clazz, res::add);
        return res;
    }
}
