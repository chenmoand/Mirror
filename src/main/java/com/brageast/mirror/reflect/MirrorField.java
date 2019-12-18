package com.brageast.mirror.reflect;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;
import com.brageast.mirror.interfaces.AbstractMirrorType;
import com.brageast.mirror.interfaces.MirrorEntity;
import com.brageast.mirror.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class MirrorField<T, C> extends AbstractMirrorType<T, Field, C> {

    /**
     * 参数类型
     */
    private Class<C> parameterType;
    /**
     * 参数
     */
    private C parameter;

    @Override
    public MirrorField<T, C> off() {
        return (MirrorField<T, C>) super.off();
    }

    public MirrorField(Field target) {
        super(target);
        accessible0();
    }

    /**
     * @param initObj           实例化对象
     * @param mirror            Mirror实例化对象
     * @param name              属性名称
     * @param parameter         参数
     * @param throwableFunction 异常回调
     */
    public MirrorField(T initObj, Mirror<T> mirror, String name, C parameter, ThrowableFunction throwableFunction) {
        this.initObj = initObj;
        this.mirror = mirror;
        doParameter(parameter);
        try {
            this.target = initObj.getClass().getDeclaredField(name);
            accessible0();
        } catch (NoSuchFieldException e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
    }

    public MirrorField(T initObj, Mirror<T> mirror, Field field) {
        this.initObj = initObj;
        this.mirror = mirror;
        this.target = field;
        accessible0();
    }

    public MirrorField<T, C> doParameter(C parameter) {
        this.parameterType = (Class<C>) ClassUtil.getClassTypes(new Object[]{parameter})[0];
        this.parameter = parameter;
        return this;
    }

    @SafeVarargs
    public final MirrorField<T, C> doAnnotations(Class<? extends Annotation>... annotations) {
        return (MirrorField<T, C>) super.doAnnotations(annotations);
    }

    public static <E, H> MirrorField<E, H> of(E initObj, Mirror<E> mirror, String name, H objValue) {
        return new MirrorField<>(initObj, mirror, name, objValue, null);
    }

    public static <E> MirrorField<E, Object> of(E initObj, Mirror<E> mirror, Field field) {
        return new MirrorField<>(initObj, mirror, field);
    }

    /**
     * 判断是否与FieldType相等
     *
     * @param fieldType
     * @return
     */
    public boolean eqFieldType(Class<?> fieldType) {
        return this.target.getType() == fieldType;
    }

    /**
     * 执行这个属性的set get方法
     *
     * @param invObj            执行的对象, 如果为空, 讲使用默认的对象
     * @param throwableFunction 异常处理
     * @param toValueFunction   返回值处理
     * @return
     */
    @Override
    public Mirror<T> invoke(Object invObj, ThrowableFunction throwableFunction, ToValueFunction<C> toValueFunction) {
        try {
            C obj = null;
            if (invObj != null) {
                target.set(invObj, parameter);
                obj = (C) target.get(invObj);
            } else if (this.initObj != null) {
                target.set(initObj, parameter);
                obj = (C) target.get(initObj);
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

        } catch (IllegalAccessException e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }

        return this.mirror;
    }

    private void invoke0(Object invObj, MirrorEntity mirrorEntity) throws IllegalAccessException {
        Object obj, value;
        obj = this.target.get(invObj);
        value = mirrorEntity.onFieldModify(obj);
        this.target.set(invObj, value);
        obj = this.target.get(invObj);
        mirrorEntity.onModifyResult(obj);
    }

}
