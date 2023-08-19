package chapter1;

/**
 * HelloWorld
 *
 * @author wangfarui
 * @since 2023/2/21
 */
public class HelloWorld {

    public final int num = 1;

    public final int num2;

    /**
     * 1. 没有返回类型
     * 2. 方法名跟class类名要一模一样（包括大小写）
     */
    public HelloWorld() {
        // this 表示当前实例，可以访问当前实例的成员变量和实例方法
        this.num2 = 2;
    }



    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
