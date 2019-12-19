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
     * 传入的类型的类
     */
    private Class<?>[] parameterTypes;

    /**
     * 传入的类型
     */
    private Object[] parameters;


    /**
     * 通过Constructor实例化
     *
     * @param target
     */
    public MirrorConstructor(Constructor<T> target) {
        super(target);
        accessible0();
    }

    public MirrorConstructor(T t, Mirror mirror, Object[] parameters) {
        this.initObj = t;
        this.mirror = (mirror == null ? Mirror.just(t) : mirror);
        doParameter(parameters);
        accessible0();
    }

    /**
     * 无视正在操作的是否是private
     *
     * @return
     */
    @Override
    public MirrorConstructor<T> off() {
        return (MirrorConstructor<T>) super.off();
    }

    /**
     * 生成要操作的对象
     *
     * @throws NoSuchMethodException
     */
    private void buildConstructor() throws NoSuchMethodException {
        if (this.target == null) {
            this.target = (Constructor<T>) this.initObj
                    .getClass()
                    .getDeclaredConstructor(this.parameterTypes);
        }
    }

    public MirrorConstructor<T> doParameter(Object... parameters) {
        this.parameterTypes = ClassUtil.getClassTypes(parameters);
        this.parameters = parameters;
        return this;
    }

    public Mirror<T> newInstance() {
        return this.newInstance(null);
    }

    public Mirror<T> newInstance(ThrowableFunction throwableFunction) {
        T t = null;
        try {
            buildConstructor();
            t = target.newInstance(parameters);
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return Mirror.just(t);
    }

    public static <E> MirrorConstructor<E> of(E e, Object... parameters) {
        return new MirrorConstructor<>(e, null, parameters);
    }

    /**
     * 不要调用这个方法
     *
     * @param invObj
     * @param throwableFunction
     * @param toValueFunction
     * @return
     */
    @Override
    @Deprecated
    public Mirror<T> invoke(Object invObj, ThrowableFunction throwableFunction, ToValueFunction<T> toValueFunction) {
        try {
            throw new ProhibitedUseException("禁止使用此方法");
        } catch (ProhibitedUseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 不要用这个方法
     *
     * @param invObj
     * @param mirrorEntity
     * @param throwableFunction
     * @return
     */
    @Override
    @Deprecated
    public Mirror<T> invoke(Object invObj, MirrorEntity mirrorEntity, ThrowableFunction throwableFunction) {
        try {
            throw new ProhibitedUseException("禁止使用此方法");
        } catch (ProhibitedUseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
