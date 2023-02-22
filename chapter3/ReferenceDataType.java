/**
 * 引用数据类型
 *
 * @author wangfarui
 * @since 2023/2/22
 */
public class ReferenceDataType {

    private String str;

    public void printString() {
        System.out.println("str: " + this.str);

        String innerStr = "1";
        this.str = innerStr;
        System.out.println("innerStr: " + innerStr);
        System.out.println("str：" + this.str);

        // 新建另一个String对象实例
        String otherStr = "2";
        // 把otherStr赋值给innerStr, this.str再输出会是什么?
        innerStr = otherStr;
        System.out.println("otherStr: " + otherStr);
        System.out.println("innerStr: " + innerStr);
        System.out.println("str：" + this.str);
    }

    public static void main(String[] args) {
        ReferenceDataType referenceDataType = new ReferenceDataType();
        referenceDataType.printString();
    }
}
