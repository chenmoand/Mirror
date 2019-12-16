package com.brageast.mirror.test;


import com.brageast.mirror.Mirror;
import com.brageast.mirror.interfaces.MirrorEntity;
import com.brageast.mirror.util.Convert;

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
                .doAnnotations(Boom.class)
                .invoke(new MirrorEntity() {
                    private Boom boom;

                    @Override
                    public Object[] onMethodModify() {
                        return new Object[]{Convert.conver(boom.num())}; //设置方法参数
                    }

                    @Override
                    public void onModifyResult(Object entity) {
                        System.out.println(entity); //得到返回
                    }
                });

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
