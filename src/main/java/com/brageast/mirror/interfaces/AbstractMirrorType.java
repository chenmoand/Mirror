package com.brageast.mirror.interfaces;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;

import java.lang.reflect.AccessibleObject;

public abstract class AbstractMirrorType<T, M extends AccessibleObject, C> implements MirrorType<T> {
    // 实例对象的类
    protected T initObj;

    // 传进的Mirror
    protected Mirror<T> mirror;

    protected M target;


    @Override
    public Mirror<T> invoke() {
        return this.invoke(null, null, null);
    }


    public Mirror<T> invoke(Object invObj, ToValueFunction<C> toValueFunction) {
        return this.invoke(invObj, null, toValueFunction);
    }

    public Mirror<T> invoke(ToValueFunction<C> toValueFunction) {
        return this.invoke(null, null, toValueFunction);
    }

    public abstract Mirror<T> invoke(Object invObj, ThrowableFunction throwableFunction, ToValueFunction<C> toValueFunction);
    public M getTarget() {
        return target;
    }
}
