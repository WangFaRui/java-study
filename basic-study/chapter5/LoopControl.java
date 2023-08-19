package chapter5;

import java.util.Arrays;
import java.util.Random;

/**
 * 循环控制
 *
 * @author wangfarui
 * @since 2023/2/23
 */
public class LoopControl {

    /**
     * 填充数据数组
     *
     * @param arr 数组
     */
    public void fillArrayData(int[] arr) {
        // 定义随机数生成器
        Random random = new Random();
        for (int i = 0, len = arr.length; i < len; i++) {
            // 给数组每个元素都赋随机数值
            arr[i] = random.nextInt(10);
        }
    }

    /**
     * 使用 for 循环展示
     *
     * @param arr 数组数据
     */
    public void showByFor(int[] arr) {
        int n = 0;
        System.out.println("数组所有数据为: " + Arrays.toString(arr));
        // a = arr[0] ; a = arr[1]; ..... a = arr[4]
        for (int a : arr) {
            n++;
            if (a < 2 || a > 7) {
                System.out.printf("[judgeNumberByIf][for-break]执行到第%d次，满足if条件的值: %d, 跳出循环。\n", n, a);
                break;
            }
            ConditionControl.judgeNumberByIf(a);
        }
    }

    /**
     * 使用 while 循环展示
     *
     * @param arr 数组数据
     */
    public void showByWhile(int[] arr) {
        int i = 0;
        int len = arr.length; // 5
        while (i < len) {
            if (arr[i] < 2 || arr[i] > 7) {
                System.out.printf("[judgeNumberBySwitch][while-break]执行到第%d次，满足if条件的值: %d, 跳出循环。\n", i + 1, arr[i]);
                break;
            }
            ConditionControl.judgeNumberBySwitch(arr[i]);
            i++;
        }

        i = 0;
        do {
            if (arr[i] < 2 || arr[i] > 7) {
                System.out.printf("[judgeNumberByIf][while-continue]执行到第%d次，满足if条件的值: %d, 继续下一次循环而不终止。\n", i + 1, arr[i]);
                continue;
            }
            ConditionControl.judgeNumberByIf(arr[i]);
        } while (++i < len);
        // i++ < len => i<len; i=i+1;
        // ++i < len => i=i+1; i<len;
    }

    public static void main(String[] args) {
        // 实例化一个 LoopControl 对象
        LoopControl loopControl = new LoopControl();
        // 定义一个长度为5的int类型数组
        int[] arr = new int[5];
        // 调用 fillArrayData 方法，用于填充数据
        loopControl.fillArrayData(arr);

        // 使用 for 形式展示
        loopControl.showByFor(arr);

        // 使用 while 形式展示
        loopControl.showByWhile(arr);
    }
}
