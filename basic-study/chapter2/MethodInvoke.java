package chapter2;

/**
 * 方法调用
 *
 * @author Wray
 * @since 2023/3/7
 */
public class MethodInvoke {

    public int getPositiveNumber(int n) {
        int sum = 0;
        if (n > 0) {
            return n + this.getPositiveNumber(n-1);
        }
        return sum;
    }

    public void getA() {
        this.getB();
        getB();
    }

    public void getB() {
        print();
        MethodInvoke.print();
        // 不建议
        this.print();
    }

    public static void print() {
        System.out.println("print");
    }

    public static void main(String[] args) {
        MethodInvoke instance = new MethodInvoke();
        int sum = instance.getPositiveNumber(5);
        System.out.println(sum);

        instance.getA();
    }
}
