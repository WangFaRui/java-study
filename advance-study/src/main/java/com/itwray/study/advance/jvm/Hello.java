package com.itwray.study.advance.jvm;

/**
 * 分析字节码文件
 *
 * @author wangfarui
 * @since 2024/6/18
 */
public class Hello {
    // 使用idea插件（jclasslib）查看.class字节码文件
    // 使用 xxd Hello.class 命令查看字节码文件
    // 使用jdk提供的 javap 命令反编译类文件
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
