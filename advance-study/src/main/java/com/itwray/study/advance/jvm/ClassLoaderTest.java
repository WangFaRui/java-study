package com.itwray.study.advance.jvm;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类加载器与instanceof关键字演示
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object obj = myLoader.loadClass("com.itwray.study.advance.jvm.ClassLoaderTest").newInstance();
        // 输出结果：class com.itwray.study.advance.jvm.ClassLoaderTest
        System.out.println(obj.getClass());
        // 输出结果：false
        System.out.println(obj instanceof com.itwray.study.advance.jvm.ClassLoaderTest);
    }
}