package com.brageast.mirror.reflect;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;
import com.brageast.mirror.interfaces.AbstractMirrorType;
import com.brageast.mirror.interfaces.MirrorEntity;
import com.brageast.mirror.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MirrorMethod<T, C> extends AbstractMirrorType<T, Method, C> {

    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 参数
     */
    private Object[] parameters;

    public MirrorMethod(Method method) {
        super(method);
        accessible0();
    }

    @Override
    public MirrorMethod<T, C> off() {
        return (MirrorMethod<T, C>) super.off();
    }


    public MirrorMethod(T initObj, Mirror<T> mirror, String name, Object[] parameters, ThrowableFunction throwableFunction) {
        this.initObj = initObj;
        this.mirror = mirror;
        doParameter(parameters);
        try {
            this.target = initObj.getClass().getDeclaredMethod(name, parameterTypes);
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

    /**
     * 判断返回类型是否相同
     *
     * @param returnType 返回类
     * @return
     */
    public boolean eqReturnType(Class<?> returnType) {
        return this.target.getReturnType() == returnType;
    }

    public MirrorMethod<T, C> doParameter(Object... parameters) {

        this.parameterTypes = ClassUtil.getClassTypes(parameters);
        this.parameters = parameters;

        return this;
    }

    /**
     * 只要有这里面其中一个注解就会加入到annotationHashMap里面
     *
     * @param annotations
     * @return
     */
    @Override
    public MirrorMethod<T, C> doAnnotations(Class<? extends Annotation>... annotations) {
        return (MirrorMethod<T, C>) super.doAnnotations(annotations);
    }


    @Override
    public Mirror<T> invoke(Object invObj, ThrowableFunction throwableFunction, ToValueFunction<C> toValueFunction) {
        try {
            C obj = null;
            if (invObj != null) {
                obj = (C) target.invoke(invObj, parameters);
            } else if (this.initObj != null) {
                obj = (C) target.invoke(initObj, parameters);
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
            if (invObj == null) {
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
        doParameter(mirrorEntity.onMethodModify());
        obj = this.target.invoke(invObj, this.parameters);
        mirrorEntity.onModifyResult(obj);
    }
}
