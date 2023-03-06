/**
 * 变量B类
 *
 * @author Wray
 * @since 2023/3/3
 */
public class VarB {
    private String myName;
    private int myAge;

    public static void main(String[] args) {
        VarA a = new VarA();
        a.age = 1;
        a.name = "jj";
        System.out.println("a.name: " + a.name);
        System.out.println("a.age: " + a.age);
    }
}
