package com.brageast.mirror.reflect;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.MirrorFile;
import com.brageast.mirror.entity.Value;
import com.brageast.mirror.function.InvokeFunction;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;
import com.brageast.mirror.abstracts.AbstractMirrorOperation;
import com.brageast.mirror.interfaces.MirrorOperation;
import com.brageast.mirror.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class MirrorField<T, C> extends AbstractMirrorOperation<T, Field, C> implements MirrorOperation<T, C> {

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
    }

    /**
     * @param initObj           实例化对象
     * @param mirror            Mirror实例化对象
     * @param name              属性名称
     * @param parameter         参数
     *
     */
    public MirrorField(T initObj, Mirror<T> mirror, String name, C parameter) {
        this.initObj = initObj;
        this.mirror = mirror;
        this.name = name;
        doParameter(parameter);
    }


    private void buildField(ThrowableFunction throwableFunction) {
        try {
            Class<?> cls = initObj.getClass();
            if(target == null) {
                this.target = isUseDeclared ?
                        cls.getDeclaredField(name) :
                        cls.getField(name);
            }
            accessible0();
            if(this.accessible) target.setAccessible(true);
        } catch (NoSuchFieldException e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
    }

    @Override
    public MirrorField<T, C> notUseDeclared() {
        return (MirrorField<T, C>)super.notUseDeclared();
    }

    @Override
    public <H extends Annotation> MirrorField<T, C> getAannotation(Class<H> cls, ToValueFunction<H> toValueFunction) {
        buildField(null);
        return (MirrorField<T, C>)super.getAannotation(cls, toValueFunction);
    }

    /**
     * @param initObj 实例
     * @param mirror  Mirror操作类
     * @param field   方法
     */
    public MirrorField(T initObj, Mirror<T> mirror, Field field) {
        this.initObj = initObj;
        this.mirror = mirror;
        this.target = field;
    }

    /**
     * 设置参数
     *
     * @param parameter 参数
     * @return
     */
    public MirrorField<T, C> doParameter(C parameter) {
        Value<C> classType = ClassUtil.getClassType(parameter);
        this.parameterType = classType.getTypeClass();
        this.parameter = classType.getTypeValue();
        return this;
    }

    @Override
    public <E extends Annotation> boolean hasAnntation(Class<E> annotation) {
        buildField(null);
        return super.hasAnntation(annotation);
    }

    /**
     * 只要有这里面其中一个注解就会加入到annotationHashMap里面
     *
     * @param annotations
     * @return
     */
    public MirrorField<T, C> doAnnotations(Class<? extends Annotation>... annotations) {
        return (MirrorField<T, C>) super.doAnnotations(annotations);
    }

    public static <E, H> MirrorField<E, H> of(E initObj, Mirror<E> mirror, String name, H objValue) {
        return new MirrorField<>(initObj, mirror, name, objValue);
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
        buildField(throwableFunction);
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


    /**
     * 执行这个属性
     *
     * @param invObj            实例化对象
     * @param invokeFunction      mirror实体对象
     * @param throwableFunction 异常处理
     * @return
     */
    @Override
    public Mirror<T> invoke(Object invObj, InvokeFunction invokeFunction, ThrowableFunction throwableFunction) {
        buildField(throwableFunction);
        setMirrorEntityAnnotation(invObj, invokeFunction);
        try {
            if(this.accessible) target.setAccessible(true);
            if (invObj == null) {
                invoke0(this.initObj, invokeFunction);
            } else {
                invoke0(invObj, invokeFunction);
            }

        } catch (IllegalAccessException e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }

        return this.mirror;
    }

    @Override
    public C getValue() {
        try {
            return (C)target.get(initObj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void invoke0(Object invObj, InvokeFunction invokeFunction) throws IllegalAccessException {
        Object obj, value;
        obj = this.target.get(invObj);
        value = invokeFunction.onFieldModify(obj);
        this.target.set(invObj, value);
        obj = this.target.get(invObj);
        invokeFunction.onModifyResult(obj);
    }

}
