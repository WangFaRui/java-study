package chapter5;

import java.util.Random;

/**
 * Java条件控制
 *
 * @author wangfarui
 * @since 2023/2/23
 */
public class ConditionControl {

    /**
     * 通过if判断数值大小
     *
     * @param i 数值
     */
    public static void judgeNumberByIf(int i) {
        System.out.print("[judgeNumberByIf]");
        if (i < 5) {
            System.out.println("这个数小于5， 它是: " + i);
        } else if (i < 7) {
            System.out.println("这个数小于7， 它是: " + i);
        } else {
            System.out.println("这个数挺大的，它是:" + i);
        }
    }

    /**
     * 通过switch判断数值大小
     *
     * @param i 数值
     */
    public static void judgeNumberBySwitch(int i) {
        System.out.print("[judgeNumberBySwitch]");
        switch (i) {
            case 1:
                System.out.println("它是1");
                break;
            case 2:
                System.out.println("它是2");
                break;
            case 3:
                System.out.println("它是3");
                break;
            default:
                System.out.println("判断不了这个数: " + i);
        }
    }

    public static void main(String[] args) {
        // 生成一个随机数
        Random random = new Random();
        int i = random.nextInt(10);

        ConditionControl.judgeNumberByIf(i);
        ConditionControl.judgeNumberBySwitch(i);
    }
}
