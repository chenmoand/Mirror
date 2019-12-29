package com.brageast.mirror.test;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.entity.Convert;

import java.net.MalformedURLException;

import static com.brageast.mirror.entity.Convert.conver;

public class Test {
    public static void main(String[] args) throws MalformedURLException {

        /*MyUser myUser = new MyUser();
        myUser.setAge(2);

        Mirror.just(myUser)
                .doOneMethod("getAge")
                .invoke(System.out::print);*/
        MyUser myUser = new MyUser();
        Object dog = Mirror.just(myUser)
                .doOneField("dog", conver(18))
//                .notUseDeclared()
                .off()
                .getValue();
        System.out.println((Integer) dog);
        System.out.println(myUser);
    }
}
