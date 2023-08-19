package chapter2;

/**
 * Java关键字
 *
 * @author wangfarui
 * @since 2023/2/21
 */
public class Keyword {

    private int age1;

    protected int age2;

    int age3;

    public int age4;

    public static int age5;

    public final int age6 = 6;

    /**
     * final修饰的变量，需要提前初始化，直接赋值初始化或者在构造方法中初始化
     */
    public final int age7;

    /**
     * static修饰的变量，可以在static方法块中赋值、调用
     */
    static {
        Keyword.age5 = 5;
    }


    public Keyword() {
        this.age7 = 7;
    }

    /**
     * static方法，只能调用static修饰的变量
     * @return age5
     */
    public static int getStaticAge() {
        Keyword keyword = new Keyword();
        keyword.age1 = Keyword.age5;
        System.out.println(keyword.age1);
        return keyword.age1;
    }

    /**
     * final方法，不能被子类重写
     * @return this.age1
     */
    public final int getFinalAge() {
        return this.age1;
    }

    public void invokeStaticClazz() {
        int k = StaticKeyword.getK();
        System.out.println(k);
        StaticKeyword staticKeyword = new StaticKeyword();
        String y = staticKeyword.getY();
        System.out.println(y);
    }

    /**
     * 抽象类才能有抽象方法
     */
    abstract class AbstractKeyword {
        /**
         * 抽象方法不需要实现
         * @return int年龄
         */
        abstract int getAge();
    }

    /**
     * 内部静态类
     * <p>static用来修饰类时，只能用于内部类</p>
     */
    static class StaticKeyword {
        static int getK() {
            return 1;
        }

        String getY() {
            return "y";
        }
    }
}
