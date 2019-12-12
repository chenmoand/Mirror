package com.brageast.mirror.reflect;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;
import com.brageast.mirror.interfaces.AbstractMirrorType;
import com.brageast.mirror.util.ClassUtil;

import java.lang.reflect.Field;

public class MirrorField<T, C> extends AbstractMirrorType<T, Field, C> {

    // 修改属性的类
    private Class<C> objClass;
    // 修改属性的值
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
                target.set(invObj, objValue);
                obj = (C) target.get(invObj);
            } else if (this.initObj != null) {
                target.set(initObj, objValue);
                obj = (C) target.get(initObj);
            }
            ToValueFunction.isNull(obj, toValueFunction);
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return this.mirror;
    }
}
