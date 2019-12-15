package com.brageast.mirror.reflect;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;
import com.brageast.mirror.interfaces.AbstractMirrorType;
import com.brageast.mirror.interfaces.MirrorEntity;
import com.brageast.mirror.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MirrorMethod<T, C> extends AbstractMirrorType<T, Method, C> {

    /**
     * 传入的类型和类型的类
     */
    private Class<?>[] classTypes;
    private Object[] objects;

    public MirrorMethod(Method method) {
        super(method);
        accessible0();
    }


    public MirrorMethod(T initObj, Mirror<T> mirror, String name, Object[] objects, ThrowableFunction throwableFunction) {
        this.initObj = initObj;
        this.mirror = mirror;
        doObjTypes(objects);
        try {
            this.target = initObj.getClass().getDeclaredMethod(name, classTypes);
            accessible0();
        } catch (NoSuchMethodException e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
    }

    public MirrorMethod(T initObj, Mirror<T> mirror, Method method) {
        this.initObj = initObj;
        this.mirror = mirror;
        this.target = method;
        accessible0();
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

    @Override
    public MirrorMethod<T, C> doAnnotations(Class<? extends Annotation>... annotations) {
        return (MirrorMethod<T, C>) super.doAnnotations(annotations);
    }

    @Override
    public Mirror<T> invoke(Object invObj, ThrowableFunction throwableFunction, ToValueFunction<C> toValueFunction) {
        try {
            C obj = null;
            if (invObj != null) {
                obj = (C) target.invoke(invObj, objects);
            } else if (this.initObj != null) {
                obj = (C) target.invoke(initObj, objects);
            }
            ToValueFunction.isNull(obj, toValueFunction);
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return this.mirror;
    }


    @Override
    public Mirror<T> invoke(Object invObj, MirrorEntity mirrorEntity, ThrowableFunction throwableFunction) {
        setMirrorEntityAnnotation(invObj, mirrorEntity);
        try {
            if(invObj == null ) {
                invoke0(this.initObj, mirrorEntity);
            } else {
                invoke0(invObj, mirrorEntity);
            }
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }

        return this.mirror;
    }
    private void invoke0(Object invObj, MirrorEntity mirrorEntity) throws Exception {
        Object obj;
        Object[] value;
        value = mirrorEntity.onMethodModify();
        obj = this.target.invoke(invObj, value);
        mirrorEntity.onModifyResult(obj);
    }
}
