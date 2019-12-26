package com.brageast.mirror.reflect;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;
import com.brageast.mirror.abstracts.AbstractMirrorOperation;
import com.brageast.mirror.function.InvokeFunction;
import com.brageast.mirror.interfaces.MirrorOperation;
import com.brageast.mirror.util.ClassUtil;

import java.awt.print.PrinterAbortException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MirrorMethod<T, C> extends AbstractMirrorOperation<T, Method, C> implements MirrorOperation<T, C> {

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


    public MirrorMethod(T initObj, Mirror<T> mirror, String name, Object[] parameters) {
        this.initObj = initObj;
        this.mirror = mirror;
        this.name = name;
        doParameter(parameters);
    }

    public MirrorMethod(T initObj, Mirror<T> mirror, Method method) {
        this.initObj = initObj;
        this.mirror = mirror;
        this.name = method.getName();
        this.target = method;
        accessible0();
    }

    public static <E, W> MirrorMethod<E, W> of(E initType, Mirror<E> mirror, String name, Object[] objects) {
        return new MirrorMethod<>(initType, mirror, name, objects);
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

    @Override
    public <H extends Annotation> MirrorMethod<T, C> getAannotation(Class<H> cls, ToValueFunction<H> toValueFunction) {
        return (MirrorMethod<T, C>)super.getAannotation(cls, toValueFunction);
    }

    public MirrorMethod<T, C> doParameter(Object... parameters) {

        this.parameterTypes = ClassUtil.getClassTypes(parameters);
        this.parameters = parameters;

        return this;
    }

    /**
     * 生成要操作的对象
     *
     * @throws NoSuchMethodException
     */
    private void buildMethod() throws NoSuchMethodException {
        if (this.target == null) {
            this.target = this.initObj
                    .getClass()
                    .getDeclaredMethod(this.name, this.parameterTypes);
            if(this.accessible) target.setAccessible(true);
        }
    }

    /**
     * 只要有这里面其中一个注解就会加入到annotationHashMap里面
     *
     * @param annotations
     * @return
     */
    @Override
    public MirrorMethod<T, C> doAnnotations(Class<? extends Annotation>... annotations) {
        try {
            buildMethod();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return (MirrorMethod<T, C>) super.doAnnotations(annotations);
    }


    @Override
    public Mirror<T> invoke(Object invObj, ThrowableFunction throwableFunction, ToValueFunction<C> toValueFunction) {
        try {
            buildMethod(); // 最后生成
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
    public Mirror<T> invoke(Object invObj, InvokeFunction invokeFunction, ThrowableFunction throwableFunction) {
        setMirrorEntityAnnotation(invObj, invokeFunction);
        try {
            buildMethod(); // 最后生成
            if (invObj == null) {
                invoke0(this.initObj, invokeFunction);
            } else {
                invoke0(invObj, invokeFunction);
            }
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }

        return this.mirror;
    }

    @Override
    @Deprecated
    public C getValue() {
        try {
            throw new PrinterAbortException("请使用invoke方法获取value");
        } catch (PrinterAbortException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void invoke0(Object invObj, InvokeFunction invokeFunction) throws Exception {
        doParameter(invokeFunction.onMethodModify(this.parameters));
        Object obj = this.target.invoke(invObj, this.parameters);
        invokeFunction.onModifyResult(obj);
    }
}
