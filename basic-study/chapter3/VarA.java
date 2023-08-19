package chapter3;

/**
 * 变量A类
 *
 * @author Wray
 * @since 2023/3/3
 */
public class VarA {
    public String name;
    public int age;

    public void setAge(int age) {
        if (age < 0 || age > 100) {
            throw new IllegalArgumentException("age范围有误");
        }
        this.age = age;
    }

    public int getAge1() {
        // 定义一个局部变量age，赋值为1
        int age = 1;
        // 返回age局部变量，返回结果就是1
        return age;
    }
    public int getAge2() {
        // 1就是在返回时定义的int变量
        return 1;
    }
    public String getName1() {
        return "a";
    }

}
