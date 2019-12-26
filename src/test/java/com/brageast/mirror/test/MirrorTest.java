package com.brageast.mirror.test;


import com.brageast.mirror.Mirror;
import com.brageast.mirror.entity.Convert;
import com.brageast.mirror.entity.Null;
import com.brageast.mirror.function.InvokeFunction;

import java.lang.reflect.Field;

public class MirrorTest {
    public static void main(String[] args) {
        User user = new User();
        Mirror.just(user)
                .defaultOffAll()
                .doOneField("name", Null.isNull(String.class))
                .invoke();
        Mirror.just(user)
                .withReturnTypeMethod(String.class)
                .forEach(mm -> mm.invoke(System.out::println));

        Mirror.just(user)
                .defaultOffAll()
                .allField(userObjectMirrorField -> userObjectMirrorField.hasAnntation(Boom.class))
                .forEach(mirrorField ->
                        mirrorField.invoke(new InvokeFunction() {
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

        Mirror.just(user)
                .doOneMethod("setName", "java")
                .invoke();


        User user1 = Mirror.just(user)
                .doConstructor("兰陵王", Convert.conver(16), "女") // 预操作
                .newInstance() // 实例化这个
                .getInstance(); // 获得这个实例

        Mirror.just(user)
                .withReturnTypeMethod(String.class);

        System.out.println(user1);


        Mirror.just(user)
                .defaultOffAll();

        Mirror.just(user)
                .withTypeField(String.class);

        Mirror.just(user)
                .doOneMethod("setName")
                .doParameter("hhaha")
                .doAnnotations(Boom.class)
                .invoke(new InvokeFunction() {
                    private Boom boom;

                    @Override
                    public Object[] onMethodModify(Object[] parameters) {
                        return new Object[]{parameters[0] + boom.value()};
                    }

                    @Override
                    public void onModifyResult(Object entity) {
                        System.out.println(entity); //得到返回
                    }
                });
        System.out.println(user);
        System.out.println(int[].class);

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
