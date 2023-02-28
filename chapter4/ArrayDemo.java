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



    private static int[][][] DYNAMIC_MULTIPLE_ARRAY = new int[3][3][6];

    public void printMultipleArray() {
        int[][] a1 = MULTIPLE_ARRAY[0];
        System.out.println("a1的长度: " + a1.length); // a1的长度: 2
        int[] a2 = a1[0];
        System.out.println("a2的长度: " + a2.length); // a2的长度: 4
        int a3 = a2[0];
        System.out.println("a3的值: " + a3); // a3的值: 1
    }

    public void printDynamicMultipleArray() {
        System.out.println("----------------------");
        int[][] a1 = DYNAMIC_MULTIPLE_ARRAY[1];
        System.out.println("a1的长度: " + a1.length); // a1的长度: 3
        int[] a2 = a1[1];
        System.out.println("a2的长度: " + a2.length); // a2的长度: 6
        int a3 = a2[1];
        System.out.println("a3的值: " + a3); // a3的值: 0
    }

    /**
     * 多维数组
     */
    private static int[][][] MULTIPLE_ARRAY = {
            {
                    {1, 11, 111, 1111}, {2, 22}
            },
            {
                    {3}
            },
            {
                    {1, 11, 111, 1111}, {2, 22}, {3, 4, 5, 6, 7, 7}
            }
    };

    /**
     * 打印3维数组
     */
    public void print4Array() {
        for (int[][] a2 : MULTIPLE_ARRAY) {
            for (int[] a3 : a2) {
                for (int a4 : a3) {
                    System.out.println(a4);
                }
            }
        }
    }

    /**
     * 打印数组下标
     * //  private int[] a4 = {1, 2, 3};
     */
    public void printArrayIndex() {
        System.out.println("a4的数组长度: " + a4.length);
        for (int i = 0, size = a4.length; i < size; i++) {
            System.out.printf("数组下标为a4[%d]的值为: %d%n", i, a4[i]);
        }
    }

    /**
     * 使用{@link java.util.Arrays}功能
     */
    public void printArraysFunction() {
        int[] array = {5, 1, 4, 2, 3};
        System.out.println("直接输出: " + array);
        System.out.println("排序前: " + Arrays.toString(array));
        // 对array排序, 默认升序
        Arrays.sort(array);
        // 使用数组打印方法
        System.out.println("排序后: " + Arrays.toString(array));
    }

    public static void main(String[] args) {
        ArrayDemo arrayDemo = new ArrayDemo();
        arrayDemo.printMultipleArray();
        arrayDemo.printDynamicMultipleArray();
        arrayDemo.print4Array();
        arrayDemo.printArrayIndex();
        arrayDemo.printArraysFunction();
    }
}
