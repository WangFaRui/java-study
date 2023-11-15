package com.itwray.study.advance.inner;

public class OuterClass extends SuperClass {
    private int value = 10;

    public int getValue() {
        return this.value;
    }

    public void printInner() {
        System.out.println("OuterClass printInner: " + OuterClass.this.getValue());
    }

    public void printSuper() {
        System.out.println("OuterClass printSuper: " + OuterClass.super.getValue());
    }

    public class InnerClass extends SuperClass {
        private int value = 20;

        public void printValues() {
            System.out.println("InnerClass value: " + this.value);
            System.out.println("OuterClass value: " + OuterClass.this.value);
            System.out.println("InnerClass SuperClass value: " + super.getValue());
            System.out.println("OuterClass SuperClass value: " + OuterClass.super.getValue());
            System.out.println("OuterClass getValue: " + OuterClass.this.getValue());
            OuterClass.this.printInner();
            OuterClass.this.printSuper();
        }
    }

    public static class InnerStaticClass {
        private int value = 30;

        public void print() {
            System.out.println("InnerStaticClass value: " + this.value);
            // 编译报错
            // System.out.println("OuterClass value: " + OuterClass.this.value);
        }
    }

    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        OuterClass.InnerClass inner = outer.new InnerClass();
        inner.printValues();

        InnerStaticClass innerStaticClass = new InnerStaticClass();
        innerStaticClass.print();
    }
}

class SuperClass {
    private int value = 31;

    public int getValue() {
        return value;
    }
}