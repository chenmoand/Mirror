package com.brageast.reflect;

import com.brageast.Mirror;
import com.brageast.function.ThrowableFunction;
import com.brageast.function.ToValueFunction;
import com.brageast.util.Null;

import java.lang.reflect.Method;
import java.util.Objects;

public class MirrorMethod<T> implements MirrorType<T> {

    private T initObj;
    private Mirror<T> mirror;
    private Method method;

    private Class<?>[] classTypes;
    private Object[] objects;

    public MirrorMethod(T initObj, Mirror<T> mirror, String name, Object[] objects, ThrowableFunction throwableFunction) {
        this.initObj = initObj;
        this.mirror = mirror;
        doObjTypes(objects);
        try {
            this.method = initObj.getClass().getDeclaredMethod(name, classTypes);
            this.method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
    }

    public MirrorMethod(T initObj, Mirror<T> mirror, Method method) {
        this.initObj = initObj;
        this.mirror = mirror;
        this.method = method;
    }

    public static <E> MirrorMethod<E> of(E initType, Mirror<E> mirror, String name, Object[] objects) {
        return new MirrorMethod(initType, mirror, name, objects, null);
    }

    public static <E> MirrorMethod<E> of(E initType, Mirror<E> mirror, Method method) {
        return new MirrorMethod(initType, mirror, method);
    }

    public MirrorMethod<T> doObjTypes(Object[] objects) {
        if (objects != null) {
            int len = objects.length;
            Class<?>[] classes = new Class[len];
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
            this.classTypes = classes;
            this.objects = objects;
        }
        return this;
    }

    public Mirror<T> invoke() {
        return this.invoke(null, null, null);
    }


    public Mirror<T> invoke(Object invObj, ToValueFunction<Object> toValueFunction) {
        return this.invoke(invObj, null, toValueFunction);
    }

    public Mirror<T> invoke(ToValueFunction<Object> toValueFunction) {
        return this.invoke(null, null, toValueFunction);
    }

    public Mirror<T> invoke(Object invObj, ThrowableFunction throwableFunction, ToValueFunction<Object> toValueFunction) {
        try {
            if (invObj != null) {
                method.invoke(invObj, objects);
            } else if (this.initObj != null) {
                Object obj = method.invoke(initObj, objects);
                if (toValueFunction != null) toValueFunction.toValue(obj);
            }
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return this.mirror;
    }


    public Method getMethod() {
        return method;
    }

}
