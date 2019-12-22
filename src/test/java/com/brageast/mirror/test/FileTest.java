package com.brageast.mirror.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileTest {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("D:/java/JdbcTestTwo.java")));

        bufferedReader.lines().forEach(System.out::println);
    }

}
