package chapter3;

/**
 * 静态变量
 *
 * @author Wray
 * @since 2023/3/6
 */
public class StaticVariable {

    public static void printStatic() {
        System.out.println("StaticVariable");
    }

    public static void main(String[] args) {
        Person ming = new Person("Xiao Ming", 12);
        Person hong = new Person("Xiao Hong", 15);
        ming.number = 88;
        System.out.println(hong.number);
        hong.number = 99;
        System.out.println(ming.number);

        Person.number = 100;
        System.out.println(hong.number);
        System.out.println(ming.number);
        System.out.println(Person.number);
    }
}
