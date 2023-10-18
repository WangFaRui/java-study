package com.itwray.study.advance.reference;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Java引用对象传值操作
 * <p>
 * 修改入参对象，是否会影响原对象？
 *
 * @author Wray
 * @since 2023/10/11
 */
public class ReferenceObjectDemo {

    public void stringPrint(String s) {
        System.out.println(s);
        Date date = new Date();
        s = new SimpleDateFormat("yyyy-MM-dd").format(date);
        System.out.println(s);
    }

    /**
     * 在 Java 中，String类是不可变的（immutable）。这意味着一旦创建了一个String对象，它的值就不能被修改。当对一个String对象进行修改时，实际上是创建了一个新的String对象，并将修改后的值赋给新的对象，而原有的String对象保持不变。
     * <p>
     * 这种设计选择有一些优点，比如：
     * <p>
     * 线程安全：由于String对象不可变，多个线程可以安全地共享相同的String对象而无需进行同步。
     * <p>
     * 缓存哈希值：由于String对象的哈希值是在创建时计算并缓存的，因此可以提高字符串的哈希查找性能。
     * <p>
     * 安全性：不可变字符串可以确保字符串在传递给其他方法时不会被修改，从而防止一些潜在的安全问题。
     */
    public void stringTest() {
        String a = "aaa";
        this.stringPrint(a);
        System.out.println(a);
    }

    public void listPrint(List<String> strings) {
        strings.forEach(System.out::print);
        System.out.println("");
        strings.add("3");
        strings.forEach(System.out::print);
        System.out.println("");
    }

    public void listTest() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        this.listPrint(list);
        list.forEach(System.out::print);
        System.out.println("");
    }

    public static void main(String[] args) {
        ReferenceObjectDemo referenceObjectDemo = new ReferenceObjectDemo();
        System.out.println("======= test string");
        referenceObjectDemo.stringTest();
        System.out.println("======= test list");
        referenceObjectDemo.listTest();
    }
}
