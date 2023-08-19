package chapter3;

/**
 * 基础数据类型
 *
 * @author wangfarui
 * @since 2023/2/21
 */
public class BasicsDataType {

    private byte a1;

    private short a2;

    private int a3;

    private long a4;

    private float a5;

    private double a6;

    private boolean a7;

    private char a8;

    /**
     * 输出打印8种基础类型的值
     * <p>在没有进行赋值时，它们都具有默认值</p>
     */
    public void print() {
        System.out.println("byte: " + a1);
        System.out.println("short: " + a2);
        System.out.println("integer: " + a3);
        System.out.println("long: " + a4);
        System.out.println("float: " + a5);
        System.out.println("double: " + a6);
        System.out.println("boolean: " + a7);
        System.out.println("char: " + a8);
    }

    public static void main(String[] args) {
        BasicsDataType basicsDataType = new BasicsDataType();
        basicsDataType.print();
    }
}
