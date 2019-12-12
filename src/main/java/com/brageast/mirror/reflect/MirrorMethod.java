package com.brageast.mirror.reflect;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;
import com.brageast.mirror.interfaces.AbstractMirrorType;
import com.brageast.mirror.util.ClassUtil;

import java.lang.reflect.Method;

public class MirrorMethod<T, C> extends AbstractMirrorType<T, Method, C> {

    /**
     *  传入的类型和类型的类
     */
    private Class<?>[] classTypes;
    private Object[] objects;



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

    public static <E, W> MirrorMethod<E, W> of(E initType, Mirror<E> mirror, String name, Object[] objects) {
        return new MirrorMethod<>(initType, mirror, name, objects, null);
    }

    public static <E, W> MirrorMethod<E, W> of(E initType, Mirror<E> mirror, Method method) {
        return new MirrorMethod<>(initType, mirror, method);
    }

    public boolean eqReturnType(Class<?> returnType) {
        return this.target.getReturnType() == returnType;
    }

    public MirrorMethod<T, C> doObjTypes(Object... objects) {

        this.classTypes = ClassUtil.getClassTypes(objects);
        this.objects = objects;

        return this;
    }


    public Mirror<T> invoke(Object invObj, ThrowableFunction throwableFunction, ToValueFunction<C> toValueFunction) {
        try {
            C obj = null;
            if (invObj != null) {
                obj = (C)target.invoke(invObj, objects);
            } else if (this.initObj != null) {
                obj = (C)target.invoke(initObj, objects);
            }
            ToValueFunction.isNull(obj, toValueFunction);
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return this.mirror;
    }


}
