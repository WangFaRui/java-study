package chapter2;

/**
 * Java方法
 *
 * @author wangfarui
 * @since 2023/2/21
 */
public class Method {

    /**
     * 无参构造方法
     * <p>小括号里没有参数，代表无参构造方法。</p>
     * <p>当类没有构造方法时，默认会生成无参构造方法。</p>
     * <p>在类实例化为对象时，需要指定一个构造方法。</p>
     */
    public Method() {
    }

    /**
     * 有参构造方法
     * <p>参数是任意数量、任意类型的</p>
     * @param param 参数
     */
    public Method(String param) {

    }

    /**
     * 静态方法
     * <p>主要特点就是，可以直接通过类进行调用，不需要实例化. eg: Method.staticPrint()</p>
     */
    public static void staticPrint() {

    }

    /**
     * 实例方法
     * <p>需要通过实例化对象调用</p>
     * <p>eg: Method m = new Method(); m.instanceMethod();</p>
     */
    public void instanceMethod() {

    }
}
