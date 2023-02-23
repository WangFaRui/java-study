/**
 * Java class类的组成结构
 *
 * @author wangfarui
 * @since 2023/2/23
 */
public class ClazzStructure {

    /**
     * public -> 访问修饰符 <br/>
     * String -> 变量的数据类型 <br/>
     * a      -> 变量名 <br/>
     * =      -> 赋值操作 <br/>
     * "1"    -> String类型的值 <br/>
     * ;      -> Java语句都是以;结尾 <br/>
     */
    public String a = "1";

    /**
     * public -> 访问修饰符 <br/>
     * String -> 方法的返回类型 <br/>
     * getA   -> 方法名 <br/>
     * ()     -> 方法的格式，里面可以接任意参数 <br/>
     * {}     -> 方法体 <br/>
     * return -> 关键字，表示返回。在方法的返回类型不为void时，必须让方法return <br/>
     * this.a -> 方法要返回的值，值的类型要与方法的返回类型"一致" <br/>
     * ;      -> Java语句都是以;结尾 <br/>
     */
    public String getA() {
        return this.a;
    }

    /**
     * 该方法是Java程序的入口方法，整个语句不可变
     */
    public static void main(String[] args) {

    }
}
