/**
 * final修饰的常量数据类型
 *
 * @author wangfarui
 * @since 2023/2/22
 */
public class FinalDataType {

    private final String str = "s1";

    private final User user = new User();

    public void print() {
        System.out.println("str: " + this.str);
        // 编译报错
        // this.str = "s2";

        System.out.println("user: " + this.user);

        // final修饰的常量 不能被重新赋值
        // 但是操作常量内部的变量和方法
        this.user.setId(1);
        System.out.println("user.id: " + this.user.id);
        System.out.println("user: " + this.user);

    }

    public static void main(String[] args) {
        FinalDataType finalDataType = new FinalDataType();
        finalDataType.print();
    }

    static class User {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "User{" + "id=" + id + '}';
        }
    }
}
