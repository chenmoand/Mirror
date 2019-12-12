package com.brageast.mirror.reflect;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;
import com.brageast.mirror.interfaces.AbstractMirrorType;
import com.brageast.mirror.util.ClassUtil;

import java.lang.reflect.Method;

public class MirrorMethod<T> extends AbstractMirrorType<T, Method> {


    public MirrorMethod(T initObj, Mirror<T> mirror, String name, Object[] objects, ThrowableFunction throwableFunction) {
        this.initObj = initObj;
        this.mirror = mirror;
        doObjTypes(objects);
        try {
            this.target = initObj.getClass().getDeclaredMethod(name, classTypes);
            this.target.setAccessible(true);
        } catch (NoSuchMethodException e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
    }

    public MirrorMethod(T initObj, Mirror<T> mirror, Method method) {
        this.initObj = initObj;
        this.mirror = mirror;
        this.target = method;
    }

    public static <E> MirrorMethod<E> of(E initType, Mirror<E> mirror, String name, Object[] objects) {
        return new MirrorMethod<>(initType, mirror, name, objects, null);
    }

    public static <E> MirrorMethod<E> of(E initType, Mirror<E> mirror, Method method) {
        return new MirrorMethod<>(initType, mirror, method);
    }

    public MirrorMethod<T> doObjTypes(Object[] objects) {

        this.classTypes = ClassUtil.getClassTypes(objects);
        this.objects = objects;

        return this;
    }

    @Override
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
            Object obj = null;
            if (invObj != null) {
                obj = target.invoke(invObj, objects);
            } else if (this.initObj != null) {
                obj = target.invoke(initObj, objects);
            }
            ToValueFunction.isNull(obj, toValueFunction);
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return this.mirror;
    }


}
