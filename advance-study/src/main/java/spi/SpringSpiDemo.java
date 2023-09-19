package spi;

import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * 基于Spring的SPI机制示例
 *
 * @author Wray
 * @since 2023/9/19
 * @see SpringFactoriesLoader
 */
public class SpringSpiDemo {

    public static void main(String[] args) {
        List<EventListener> eventListeners = SpringFactoriesLoader.loadFactories(EventListener.class, null);
        String event = "Spring SPI机制";
        eventListeners.forEach(eventListener -> eventListener.onEvent(event));

        // Spring 6.0开始废弃此方法
        List<String> factoryNames = SpringFactoriesLoader.loadFactoryNames(EventListener.class, null);
        System.out.println(factoryNames);
    }
}
