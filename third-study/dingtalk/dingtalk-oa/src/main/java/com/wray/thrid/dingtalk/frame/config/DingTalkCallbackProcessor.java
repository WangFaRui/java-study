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
        Class<?>[] parameterTypes = method.getParameterTypes();
        Preconditions.checkArgument(parameterTypes.length == 1,
                "Invalid number of parameters: %s for method: %s, should be 1", parameterTypes.length,
                method);
        Preconditions.checkArgument(DingTalkCallbackEvent.class.isAssignableFrom(parameterTypes[0]),
                "Invalid parameter type: %s for method: %s, should be DingTalkCallbackDto", parameterTypes[0],
                method);

        ReflectionUtils.makeAccessible(method);

        DingTalkCallbackListener dingTalkCallbackListener = (callbackEvent) ->
                ReflectionUtils.invokeMethod(method, bean, callbackEvent);

        DingTalkConfig.addCallbackListener(annotation, dingTalkCallbackListener);
    }

    private List<Method> findAllMethod(Class<?> clazz) {
        final List<Method> res = new LinkedList<>();
        ReflectionUtils.doWithMethods(clazz, res::add);
        return res;
    }
}
