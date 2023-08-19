package chapter3;

class Person {
    public String name;
    public int age;

    public static int number;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static void printStatic() {
        System.out.println("Person");
    }
}
