package com.brageast.mirror.test;

import com.brageast.mirror.Mirror;

import static com.brageast.mirror.util.Convert.is;

public class MirrorTest {
    public static void main(String[] args) {
        User user = new User();
        Mirror.just(user)
                .doOneMethod("setName", "沉默")
                .invoke()
                .doOneMethod("setAge", is(18))
                .invoke()
                .doOneMethod("setSex", "男")
                .invoke();
        Mirror.just(user)
                .allMethod(mirrorMethod -> mirrorMethod.getTarget().getName() == "toString")
                .forEach(userMirrorMethod -> userMirrorMethod.invoke(System.out::print));
    }
}
