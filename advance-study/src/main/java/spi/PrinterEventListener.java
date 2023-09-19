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

    // Java SPI 和 Spring SPI 只走无参构造方法
    public PrinterEventListener() {
    }

    public PrinterEventListener(String msg) {
        System.out.println("PrinterEventListener Constructor method: " + msg);
    }

    @Override
    public void onEvent(String event) {
        System.out.println("this is PrinterEventListener: " + event);
    }
}
