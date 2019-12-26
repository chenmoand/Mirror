package com.brageast.mirror.test;

public class MyUser extends User {
    public void say() {
        System.out.println("Hello");
    }

    @Override
    public int getAge() {
        return super.getAge() + 5;
    }
}
