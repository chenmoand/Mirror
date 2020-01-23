package com.brageast.mirror.test;


public class Test {
    private String str = "sad";

    public static void main(String[] args) throws Exception {

//        System.out.println(ReflectionUtil.findConcreteMethodsOnInterfaces(ArrayList.class));
        Test.class.getDeclaredMethod("defaultValue").invoke(null);
    }

    public static void defaultValue() {
        System.out.println("hhaha");
    }

}

interface Demo {
}
