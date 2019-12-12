package com.brageast.mirror.test;

import com.brageast.mirror.Mirror;

public class MirrorTest {
    public static void main(String[] args) {
        User user = new User();
        Mirror.just(user)
                .doOneField("name", "哈哈哈")
                .invoke();
        Mirror.just(user)
                .allMethod(mirrorMethod -> mirrorMethod.getTarget().getName() == "toString")
                .forEach(userMirrorMethod -> userMirrorMethod.invoke(System.out::print));
    }
}
