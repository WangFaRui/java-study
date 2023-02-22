import java.util.Arrays;

/**
 * Java数组
 *
 * @author wangfarui
 * @since 2023/2/22
 */
public class ArrayDemo {

    private int[] a1;

    private int a2[];

    /**
     * 从这里可以看出, Java数组是一个引用类型。因为基础数据类型是不能new对象的。
     */
    private int[] a3 = new int[3];

    private int[] a4 = {1, 2, 3};

    /**
     * 4维数组
     */
    private static int[][][][] FOUR_ARRAY = {{{{1}}}};

    /**
     * 打印4维数组
     */
    public void print4Array() {
        for (int[][][] a1 : FOUR_ARRAY) {
            for (int[][] a2 : a1) {
                for (int[] a3 : a2) {
                    for (int a4 : a3) {
                        System.out.println(a4);
                    }
                }
            }
        }
    }

    /**
     * 打印数组下标
     */
    public void printArrayIndex() {
        System.out.println("a4的数组长度: " + a4.length);
        for (int i = 0, size = a4.length; i < size; i++) {
            System.out.printf("数组下标为[%d]的值为: %d%n", i, a4[i]);
        }
    }

    /**
     * 使用{@link java.util.Arrays}功能
     */
    public void printArraysFunction() {
        int[] array = {5, 1, 4, 2, 3};
        System.out.println("排序前: " + Arrays.toString(array));
        // 对array排序, 默认升序
        Arrays.sort(array);
        // 使用数组打印方法
        System.out.println("排序后: " + Arrays.toString(array));
    }

    public static void main(String[] args) {
        ArrayDemo arrayDemo = new ArrayDemo();
        arrayDemo.print4Array();
        arrayDemo.printArrayIndex();
        arrayDemo.printArraysFunction();
    }
}
