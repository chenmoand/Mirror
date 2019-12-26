package com.brageast.mirror.reflect;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;
import com.brageast.mirror.abstracts.AbstractMirrorOperation;
import com.brageast.mirror.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public class MirrorConstructor<T> extends AbstractMirrorOperation<T, Constructor<T>, T> {

    /**
     * 传入的类型的类
     */
    private Class<?>[] parameterTypes;

    /**
     * 传入的类型
     */
    private Object[] parameters;

    /**
     * 操作的class
     */
    private Class<T> tClass;


    /**
     * 通过Constructor实例化
     *
     * @param target
     */
    public MirrorConstructor(Constructor<T> target) {
        super(target);

    }

    public MirrorConstructor(Class<T> tClass, Mirror mirror, Object[] parameters) {
        this.tClass = tClass;
        this.mirror = (mirror == null ? Mirror.just(tClass) : mirror);
        doParameter(parameters);
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


    @Override
    public MirrorConstructor<T> notUseDeclared() {
        return (MirrorConstructor<T>)super.notUseDeclared();
    }

    /**
     * 生成要操作的对象
     *
     * @throws NoSuchMethodException
     */
    private void buildConstructor() throws NoSuchMethodException {
        if (this.target == null) {
            this.target = isUseDeclared ?
                    this.tClass.getDeclaredConstructor(this.parameterTypes) :
                    this.tClass.getConstructor(this.parameterTypes);
        }
        accessible0();
        if(!this.accessible) target.setAccessible(true);
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

    public static <E> MirrorConstructor<E> of(Class<E> tClass, Object... parameters) {
        return new MirrorConstructor<>(tClass, null, parameters);
    }

    @Override
    public <E extends Annotation> boolean hasAnntation(Class<E> annotation) {
        try {
            buildConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return super.hasAnntation(annotation);
    }

    @Override
    public MirrorConstructor<T> doAnnotations(Class<? extends Annotation>... annotations) {
        return (MirrorConstructor<T>) super.doAnnotations(annotations);
    }

    @Override
    public <H extends Annotation> MirrorConstructor<T> getAannotation(Class<H> cls, ToValueFunction<H> toValueFunction) {
        return (MirrorConstructor<T>) super.getAannotation(cls, toValueFunction);
    }


    /**
     * 直接获取实例
     *
     * @return
     */
    @Override
    public T getValue() {
        return newInstance().getInstance();
    }
}
