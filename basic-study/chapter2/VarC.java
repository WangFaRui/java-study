package chapter2;

/**
 * public类的唯一
 *
 * @author Wray
 * @since 2023/3/6
 */
class VarW {
    public static void main(String[] args) {
        System.out.println("w");
    }
}

// 匿名类
class VarD {

}

public class VarC {
    public static void main(String[] args) {
        VarD varD = new VarD();
        System.out.println("c");

    }

    public void test() {
        new VarQ();
        new VarD();

        VarP varP = new VarP();

    }

    // 静态内部类
    static class VarQ {

    }

    class VarP {
        int age;
    }
}
//
//public class VarE {
//
//}