package spi;

/**
 * 备份 事件监听器
 *
 * <p> 用于将事件消息备份存储
 *
 * @author Wray
 * @since 2023/9/18
 */
public class BackupEventListener implements EventListener {

    @Override
    public void onEvent(String event) {
        System.out.println("this is BackupEventListener: " + event);
        this.backup(event);
    }

    private void backup(String event) {
        // 保存...
    }
}
