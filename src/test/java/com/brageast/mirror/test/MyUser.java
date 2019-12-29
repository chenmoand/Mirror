package com.brageast.mirror.test;

public class MyUser extends User {
    private int dog;
    public void say() {
        System.out.println("Hello");
    }

    @Override
    public int getAge() {
        return super.getAge() + 5;
    }
}
