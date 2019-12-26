package com.brageast.mirror.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Test {
    public static void main(String[] args) throws MalformedURLException {

        /*MyUser myUser = new MyUser();
        myUser.setAge(2);

        Mirror.just(myUser)
                .doOneMethod("getAge")
                .invoke(System.out::print);*/
        File file = new File("D:\\private\\mirror\\target\\mirror-1.8.jar");
        URL url = file.toURI().toURL();
        System.out.println(url.getPath());
    }
}
