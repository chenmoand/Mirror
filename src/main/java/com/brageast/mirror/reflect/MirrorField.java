package com.brageast.mirror.reflect;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;
import com.brageast.mirror.interfaces.AbstractMirrorType;
import com.brageast.mirror.util.ClassUtil;

import java.lang.reflect.Field;

public class MirrorField<T, C> extends AbstractMirrorType<T, Field, C> {

    private Class<C> objClass;
    private C objValue;

    public MirrorField(T initObj, Mirror<T> mirror, String name, C objValue, ThrowableFunction throwableFunction) {
        this.initObj = initObj;
        this.mirror = mirror;
        doObjType(objValue);
        try {
            this.target = initObj.getClass().getDeclaredField(name);
            this.target.setAccessible(true);
        } catch (NoSuchFieldException e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
    }

    public MirrorField(T initObj, Mirror<T> mirror, Field field) {
        this.initObj = initObj;
        this.mirror = mirror;
        this.target = field;
    }

    public MirrorField<T, C> doObjType(C objValue) {
        this.objClass = (Class<C>) ClassUtil.getClassTypes(new Object[]{objValue})[0];
        this.objValue = objValue;
        return this;
    }


    public static <E, H> MirrorField<E, H> of(E initObj, Mirror<E> mirror, String name, H objValue) {
        return new MirrorField<>(initObj, mirror, name, objValue, null);
    }

    public static <E> MirrorField<E, Object> of(E initObj, Mirror<E> mirror, Field field) {
        return new MirrorField<>(initObj, mirror, field);
    }


    @Override
    public Mirror<T> invoke(Object invObj, ThrowableFunction throwableFunction, ToValueFunction<C> toValueFunction) {
        try {
            Object obj = null;
            if (invObj != null) {
                target.set(invObj, objValue);
                obj = target.get(invObj);
            } else if (this.initObj != null) {
                target.set(initObj, objValue);
                obj = target.get(initObj);
            }
            ToValueFunction.isNull(obj, toValueFunction);
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return this.mirror;
    }
}
