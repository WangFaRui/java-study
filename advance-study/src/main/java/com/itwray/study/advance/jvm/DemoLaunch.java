package com.itwray.study.advance.jvm;

/**
 * 示例的启动类
 *
 * @author wray
 * @since 2024/6/26
 */
public class DemoLaunch {

    public static void main(String[] args) {
        new ClassLoadingDemo();
        System.out.println(ClassLoadingDemo.i);
    }
}
