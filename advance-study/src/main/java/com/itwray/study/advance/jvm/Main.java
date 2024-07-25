package com.itwray.study.advance.jvm;

/**
 * 字节码分析类
 *
 * @author wray
 * @since 2024/7/25
 */
public class Main {

    private int num;

    private static final String name = "wray";

    public static void main(String[] args) {
        Main main = new Main();
        main.num++;
        main.print(name + main.num);
    }

    private void print(String arg) {
        System.out.println("Hello " + arg);
    }
}
