package spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 基于Java的SPI机制示例
 *
 * @author Wray
 * @since 2023/9/18
 */
public class JavaSpiDemo {

    public static void main(String[] args) {
        // 通过Java SPI加载
        ServiceLoader<EventListener> serviceLoader = ServiceLoader.load(EventListener.class);
        // 获取接口迭代器
        Iterator<EventListener> listenerIterator = serviceLoader.iterator();
        // 定义事件监听消息
        String event = "Java SPI机制";

        // 遍历调用事件监听器
        listenerIterator.forEachRemaining(eventListener -> {
            eventListener.onEvent(event);
        });
    }
}
