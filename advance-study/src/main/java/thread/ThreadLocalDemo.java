package thread;

/**
 * 基于{@link ThreadLocal}的示例
 *
 * @author Wray
 * @since 2023/8/19
 */
public class ThreadLocalDemo {

    private static ThreadLocal<String> LOCAL_VARIABLE = new ThreadLocal<>();

    public void setValue(String s) {
        LOCAL_VARIABLE.set(s);
    }

    public void printWithRemove() {
        System.out.println(Thread.currentThread().getName() + "变量值为：" + LOCAL_VARIABLE.get());
        LOCAL_VARIABLE.remove();
    }

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
            threadLocalDemo.setValue("a");
            threadLocalDemo.printWithRemove();
            System.out.println(Thread.currentThread().getName() + "变量值为：" + LOCAL_VARIABLE.get());
        }, "线程A").start();

        Thread.sleep(3000L);
        System.out.println(Thread.currentThread().getName() + "变量值为：" + LOCAL_VARIABLE.get());

        new Thread(() -> {
            ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
            threadLocalDemo.setValue("b");
            threadLocalDemo.printWithRemove();
            System.out.println(Thread.currentThread().getName() + "变量值为：" + LOCAL_VARIABLE.get());
        }, "线程B").start();
    }
}
