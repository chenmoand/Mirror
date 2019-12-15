package com.brageast.mirror.test;


import com.brageast.mirror.Mirror;
import com.brageast.mirror.interfaces.MirrorEntity;

import java.lang.reflect.Field;

public class MirrorTest {
    public static void main(String[] args) {
        User user = new User();
        /*Mirror.just(user)
                .doOneField("name", null)
                .invoke();
*/
        Mirror.just(user)
                .withReturnTypeMethod(String.class)
                .forEach(mm -> mm.invoke(System.out::println));

        Mirror.just(user)
                .defaultOffAll()
                .allField(userObjectMirrorField -> userObjectMirrorField.hasAnntation(Boom.class))
                .forEach(mirrorField ->
                        mirrorField.invoke(new MirrorEntity() {
                            private Boom boom; // 自动将上面筛选的注解实例注入

                            @Override
                            public Object onFieldModify(Object entity) {
                                System.out.println(boom);
                                return boom.value();
                            }

                            @Override
                            public void onModifyResult(Object entity) {
                                System.out.println(entity);
                            }
                        }));
        System.out.println(user);



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
