package com.itwray.study.advance.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * {@link Unsafe}使用示例
 *
 * @author wangfarui
 * @since 2024/6/12
 */
public class UnsafeDemo {

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = reflectGetUnsafe();
        staticTest(unsafe);
    }

    private static void staticTest(Unsafe unsafe) throws Exception {
        User user=new User();
        // 也可以用下面的语句触发类初始化
        // 1.
        // unsafe.ensureClassInitialized(User.class);
        // 2.
        // System.out.println(User.name);
        System.out.println(unsafe.shouldBeInitialized(User.class));
        Field sexField = User.class.getDeclaredField("name");
        long fieldOffset = unsafe.staticFieldOffset(sexField);
        Object fieldBase = unsafe.staticFieldBase(sexField);
        Object object = unsafe.getObject(fieldBase, fieldOffset);
        System.out.println(object);
    }

    private static Unsafe reflectGetUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
