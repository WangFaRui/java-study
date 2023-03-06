import java.util.Arrays;

/**
 * 可变参数
 *
 * @author Wray
 * @since 2023/3/3
 */
public class VariableParam {

    private String[] names;

    public void setNames(String... names) {
        this.names = names;
    }

    public void setNamesByArray(String[] names) {
        this.names = names;
    }

    public static void main(String[] args) {
        VariableParam variableParam = new VariableParam();
        variableParam.setNames(); // 空数组
        variableParam.setNames("1");
        variableParam.setNames("1", "2");
        variableParam.setNames(null); // null
        String[] s = {"a", "b"};
        variableParam.setNames(s);
//        String[] s2 = {"aa", "bb"};
//        variableParam.setNames(s, s2);
        System.out.println(Arrays.toString(variableParam.names));
    }
}
