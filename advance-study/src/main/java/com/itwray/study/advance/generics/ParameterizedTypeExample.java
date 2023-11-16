package com.itwray.study.advance.generics;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

/**
 * ParameterizedType是Java中的一个接口，它表示带有泛型参数的类型。它用于描述一个具体的泛型类型，包括泛型类、泛型接口和泛型数组等。
 * <p>
 * ParameterizedType接口定义了获取泛型类型参数的方法，以便在运行时获取泛型类型的信息。它包含以下主要方法：
 * <p>
 * Type[] getActualTypeArguments(): 返回表示泛型类型参数的Type数组。如果泛型类型有多个参数，则返回一个包含这些参数类型的数组。<p>
 * Type getRawType(): 返回原始类型，即不带泛型参数的类型。<p>
 * Type getOwnerType(): 返回表示该泛型类型所属的类型，例如内部类访问外部类的泛型类型。<p>
 *
 * 需要注意的是，getGenericSuperclass() 方法返回的是表示当前类的直接超类的泛型类型信息。
 * 如果当前类是顶级类（没有明确的直接超类），或者当前类是匿名内部类，则返回的是 java.lang.Object 类型。
 * 如果需要获取当前类自身的泛型类型信息，可以通过 getGenericInterfaces() 方法获取当前类实现的接口的泛型类型信息。
 */
public class ParameterizedTypeExample {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();

        Type type = list.getClass().getGenericSuperclass();
        // 通过判断返回的对象是否为 ParameterizedType 类型，我们可以确定直接父类是否具有泛型参数。
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();

            Type rawType = parameterizedType.getRawType();
            System.out.println("rawType: " + rawType);
            Type ownerType = parameterizedType.getOwnerType();
            System.out.println("ownerType: " + ownerType);

            for (Type argument : typeArguments) {
                System.out.println("Type argument: " + argument);
            }
        }

        System.out.println("-----------------------");

        Type[] genericInterfaces = list.getClass().getGenericInterfaces();
        for (Type argument : genericInterfaces) {
            System.out.println("genericInterfaces argument: " + argument);
        }

        TypeVariable<? extends Class<? extends List>>[] typeParameters = list.getClass().getTypeParameters();
        for (TypeVariable typeVariable : typeParameters) {
            System.out.println("TypeVariable: " + typeVariable);
        }

        System.out.println("-----------------------");

        ChildClass childClass = new ChildClass();
        Class<?> superclass = childClass.getClass().getSuperclass();
        System.out.println(superclass);
        Type genericSuperclass = childClass.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            for (Type argument : typeArguments) {
                System.out.println("parameterizedType argument: " + argument);
            }
        }
    }
}

class ParentClass<T> {
}

class ChildClass extends ParentClass<String> {
}