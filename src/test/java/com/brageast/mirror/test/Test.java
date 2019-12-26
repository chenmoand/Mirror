package com.brageast.mirror.test;

import com.brageast.mirror.Mirror;

public class Test {
    public static void main(String[] args) {

        MyUser myUser = new MyUser();
        myUser.setAge(2);

        Mirror.just(myUser)
                .doOneMethod("getAge")
                .invoke(System.out::print);
    }
}
