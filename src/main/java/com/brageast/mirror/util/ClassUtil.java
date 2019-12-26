package com.brageast.mirror.util;

import com.brageast.mirror.entity.Convert;
import com.brageast.mirror.entity.Null;
import com.brageast.mirror.entity.Value;
import com.brageast.mirror.function.ThrowableFunction;

import java.util.Objects;

public abstract class ClassUtil {

    /**
     * 获取这个数组的所有Class类型并返回成数组格式
     *
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
                } else if (objects[i] instanceof Convert) {
                    Convert convert = (Convert) objects[i];
                    classes[i] = convert.getTypeClass();
                    objects[i] = convert.getTypeValue();
                } else {
                    classes[i] = objects[i].getClass();
                }
            }
        }
        return classes;
    }

    public static <E> Value<E> getClassType(E object) {
        Value<E> value = new Value<>();

        if(object != null) {
            if(object instanceof Null) {
                value.setTypeClass((Class<E>) ((Null) object).getTypeClass());
                value.setTypeValue(null);
            } else if (object instanceof Convert) {
                Convert convert = (Convert) object;
                value.setTypeClass((Class<E>)convert.getTypeClass());
                value.setTypeValue((E) convert.getTypeValue());
            } else {
                value.setTypeClass((Class<E>) object.getClass());
                value.setTypeValue(object);
            }
        }


        return value;
    }

    public static <T> T newInstance(Class<T> tClass) {
        return newInstance(tClass, null);
    }

    public static <T> T newInstance(Class<T> tClass, ThrowableFunction throwableFunction) {
        T t = null;
        try {
            t = tClass.getConstructor().newInstance();
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return t;
    }
}
