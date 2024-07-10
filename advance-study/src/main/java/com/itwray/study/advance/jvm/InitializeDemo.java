package com.itwray.study.advance.jvm;

/**
 * 其实该类中static块的 { i=0 }操作和 { static int i = 1; }操作会被编译器重编译，翻译为以下代码：
 * static int i = 0;
 * static {
 *     System.out.println(i);
 *     i = 1;
 * }
 */
public class InitializeDemo {
    static {
        i = 0; // 给变量复制可以正常编译通过
        // System.out.print(i); // 这句编译器会提示“非法向前引用”
        System.out.println(InitializeDemo.i); // 编译可以通过，并且可执行输出为0
    }

    static int i = 1;

    static class Parent {
        public static int A = 1;
        static {
            A = 2;
            System.out.println("Parent static method");
        }
    }
    static class Sub extends Parent {
        public static int B = A;
    }

    public static void main(String[] args) throws ClassNotFoundException {
//        System.out.println(Sub.B);

//        Class<?> clazz = Class.forName("com.itwray.study.advance.jvm.Hello", false, InitializeDemo.class.getClassLoader());

//        Class<Hello> helloClass = Hello.class; // 仅解析类，不触发初始化

        System.out.println(Hello.CONST);

        System.out.println("InitializeDemo main method");
    }

    interface IA {
        int a = 3;
    }
}