package spi;

/**
 * 打印器 事件监听器
 *
 * <p> 用于打印事件内容
 *
 * @author Wray
 * @since 2023/9/18
 */
public class PrinterEventListener implements EventListener {

    @Override
    public void onEvent(String event) {
        System.out.println("this is PrinterEventListener: " + event);
    }
}
