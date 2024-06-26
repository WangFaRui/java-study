package com.itwray.study.advance.jvm;

/**
 * 类加载示例
 *
 * @author wray
 * @since 2024/6/26
 */
public class ClassLoadingDemo {

    static {
        System.out.println("execute static code block");
        // 输出：before ClassLoadingDemo.i = 0
        System.out.println("before ClassLoadingDemo.i = " + ClassLoadingDemo.i);
        i = 2;
        // 输出：after ClassLoadingDemo.i = 2
        System.out.println("after ClassLoadingDemo.i = " + ClassLoadingDemo.i);
    }

    /**
     * 静态变量的赋值在static块之后，就会覆盖static块中的赋值，最终i=1 <p>
     * !!!说明：静态变量的赋值和静态初始化块的执行顺序是按照编码顺序来的。并且如果静态变量的显式赋值在静态初始化块之后，那么静态初始化块中赋值的效果在类加载过程结束后会被覆盖。<p>
     * ps：如果把 {@code static int i = 1;} 语句放在static块之前，那么最终值则会被static块覆盖，示例中i=2 <p>
     */
    static int i = 1;

    /**
     * 运行时，实例化ClassLoadingDemo类，i就会被赋值为3
     */
    public ClassLoadingDemo() {
        System.out.println("execute constructor method");
        // 输出：before ClassLoadingDemo.i = 1
        System.out.println("before ClassLoadingDemo.i = " + ClassLoadingDemo.i);
        ClassLoadingDemo.i = 3;
        // 输出：after ClassLoadingDemo.i = 3
        System.out.println("after ClassLoadingDemo.i = " + ClassLoadingDemo.i);
    }

}
