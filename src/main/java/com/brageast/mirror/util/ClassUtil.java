package com.brageast.mirror.util;

import java.util.Objects;

public abstract class ClassUtil {

    /**
     * 获取这个数组的所有Class并返回成数组格式
     * @param objects
     * @return
     */
    public static Class<?>[] getClassTypes(Object[] objects) {
        Class<?>[] classes = null;
        if (objects != null) {
            int len = objects.length;
            classes = new Class[len];
            for (int i = 0; i < len; ++i) {
                Objects.requireNonNull(objects[i], "请不要直接传入null, 请用com.brageast.util.Null代替");
                if (objects[i] instanceof Null) {
                    Null nul = (Null) objects[i];
                    classes[i] = nul.getTypeClass();
                    objects[i] = null;
                } else {
                    classes[i] = objects[i].getClass();
                }
            }
        }
        return classes;
    }
}
