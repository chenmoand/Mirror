package com.brageast.mirror.reflect;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.exception.ProhibitedUseException;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;
import com.brageast.mirror.interfaces.AbstractMirrorType;
import com.brageast.mirror.interfaces.MirrorEntity;
import com.brageast.mirror.util.ClassUtil;

import java.lang.reflect.Constructor;

public class MirrorConstructor<T> extends AbstractMirrorType<T, Constructor<T>, T> {

    /**
     * 传入的类型和类型的类
     */
    private Class<?>[] classTypes;
    private Object[] objects;


    public MirrorConstructor(Constructor<T> target) {
        super(target);
        accessible0();
    }

    public MirrorConstructor(T t, Mirror mirror, Object[] objects) {
        this.initObj = t;
        this.mirror = (mirror == null ? Mirror.just(t) : mirror);
        doParameter(objects);
        try {
            this.target = (Constructor<T>) initObj.getClass().getConstructor(classTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        accessible0();
    }

    @Override
    public MirrorConstructor<T> off() {
        return (MirrorConstructor<T>)super.off();
    }

    public MirrorConstructor<T> doParameter(Object... objects) {
        this.classTypes = ClassUtil.getClassTypes(objects);
        this.objects = objects;
        return this;
    }

    public Mirror<T> newInstance() {
        return this.newInstance(null);
    }

    public Mirror<T> newInstance(ThrowableFunction throwableFunction) {
        T t = null;
        try {
            t = target.newInstance(objects);
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return Mirror.just(t);
    }

    public static <E> MirrorConstructor<E> of(E e, Object... objects) {
        return new MirrorConstructor<>(e, null, objects);
    }

    @Override
    public Mirror<T> invoke(Object invObj, ThrowableFunction throwableFunction, ToValueFunction<T> toValueFunction) {
        try {
            throw new ProhibitedUseException("禁止使用此方法");
        } catch (ProhibitedUseException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Mirror<T> invoke(Object invObj, MirrorEntity mirrorEntity) {
        try {
            throw new ProhibitedUseException("禁止使用此方法");
        } catch (ProhibitedUseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
