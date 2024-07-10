package com.itwray.study.advance.jvm;

/**
 * 加载自定义的 java.lang.String 类
 *
 * @author wray
 * @since 2024/7/10
 */
public class StringDemo {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> stringClass = Class.forName("java.lang.String");
        System.out.println(stringClass.getName());
    }
}
