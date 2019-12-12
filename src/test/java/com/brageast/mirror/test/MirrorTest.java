package com.brageast.mirror.test;


import com.brageast.mirror.Mirror;

import java.lang.reflect.Field;

public class MirrorTest {
    public static void main(String[] args) {
        User user = new User();
        Mirror.just(user)
                .doOneField("name", "哈哈哈")
                .invoke();

        Mirror.just(user)
                .withReturnTypeMethod(String.class)
                .forEach(mm -> mm.invoke(System.out::println));
    }

    public void test() throws Exception {
        User user = new User();
        Class<? extends User> aClass = user.getClass();
        Field name = aClass.getDeclaredField("name");
        name.setAccessible(true);
        name.set(user, "哈哈哈");

        Field[] fields = aClass.getFields();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                System.out.println(field.get(user));
            }
        }

    }

}
