package com.itwray.study.advance.emuns;

/**
 * 枚举主方法类
 *
 * @author wangfarui
 * @since 2024/1/25
 */
public class EnumsMain {

    public static void main(String[] args) {
        TypeEnum typeEnum = TypeEnum.A;
        TypeEnum typeEnum3 = TypeEnum.A;
        System.out.println("typeEnum: " + typeEnum.getName());
        typeEnum.setName("aa");
        System.out.println("typeEnum: " + typeEnum.getName());
        System.out.println("typeEnum3: " + typeEnum3.getName());

        TypeEnum typeEnum2 = TypeEnum.A;
        System.out.println("typeEnum2: " + typeEnum2.getName());
    }
}
