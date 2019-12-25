package com.brageast.mirror.interfaces;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;
import com.brageast.mirror.reflect.MirrorField;
import com.brageast.mirror.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.util.HashMap;

public abstract class AbstractMirrorType<T, M extends AccessibleObject, C> implements MirrorOperation<T, C> {

    /**
     * 实例对象的类
     */
    protected T initObj;

    /**
     * 传进的Mirror
     */
    protected Mirror<T> mirror;

    /**
     * 操作反射对象的名字
     */
    protected String name;

    /**
     * 反射所操作的对象, 如构造器, 属性, 方法
     */
    protected M target;

    /**
     * 实例化的注解存储
     */
    protected HashMap<Class<? extends Annotation>, Annotation> annotationHashMap = new HashMap<>();


    /**
     * 判断mirror是否设置了关闭所有权限检查
     */
    protected void accessible0() {
        if (mirror.defaultAccessible()) {
            target.setAccessible(true);
        }
    }

    /**
     * 无视权限
     *
     * @return
     */
    public AbstractMirrorType<T, M, C> off() {
        target.setAccessible(true);
        return this;
    }

    public AbstractMirrorType() {

    }

    protected AbstractMirrorType(M target) {
        if (target instanceof Member) {
            Member member = (Member) target;
            Class<T> declaringClass = (Class<T>) member.getDeclaringClass();
            this.mirror = Mirror.just(declaringClass);
            this.initObj = ClassUtil.newInstance(declaringClass);
            this.name = member.getName();
            this.target = target;
        }
    }

    /**
     * 返回Mirror
     *
     * @return
     */
    public Mirror<T> and() {
        return this.mirror;
    }

    /**
     * 判断是否有没有注解
     * 有返会true,没用返回false
     *
     * @param annotation
     * @param <E>
     * @return
     */
    public <E extends Annotation> boolean hasAnntation(Class<E> annotation) {
        E declaredAnnotation = target.getDeclaredAnnotation(annotation);
        if (target.getDeclaredAnnotation(annotation) != null) {
            this.annotationHashMap.put(annotation, declaredAnnotation);
            return true;
        }
        return false;
    }

    protected AbstractMirrorType<T, M, C> doAnnotations(Class<? extends Annotation>... annotations) {
        for (Class<? extends Annotation> annotation : annotations) {
            hasAnntation(annotation);
        }
        return this;
    }

    /**
     * 获得注解实例
     *
     * @param cls             注解类
     * @param toValueFunction 获得注解回调方法
     * @param <H>
     * @return
     */
    public <H extends Annotation> AbstractMirrorType<T, M, C> getAannotation(Class<H> cls, ToValueFunction<H> toValueFunction) {
        H annotation = (H) annotationHashMap.get(cls);
        toValueFunction.toValue(annotation);
        return this;
    }

    public HashMap<Class<? extends Annotation>, Annotation> getAnnotationHashMap() {
        return this.annotationHashMap;
    }

    @Override
    public Mirror<T> invoke() {
        return this.invoke(null, (ThrowableFunction) null, null);
    }

    @Override
    public Mirror<T> invoke(Object invObj, ToValueFunction<C> toValueFunction) {
        return this.invoke(invObj, null, toValueFunction);
    }

    @Override
    public Mirror<T> invoke(ToValueFunction<C> toValueFunction) {
        return this.invoke(null, null, toValueFunction);
    }

    public abstract Mirror<T> invoke(Object invObj, ThrowableFunction throwableFunction, ToValueFunction<C> toValueFunction);

    @Override
    public Mirror<T> invoke(MirrorEntity mirrorEntity) {
        return this.invoke(null, mirrorEntity);
    }

    @Override
    public Mirror<T> invoke(Object invObj, MirrorEntity mirrorEntity) {
        return this.invoke(invObj, mirrorEntity, null);
    }

    protected void setMirrorEntityAnnotation(Object invObj, MirrorEntity mirrorEntity) {
        // 先将Annotation 值注入进去
        Mirror.just(mirrorEntity)
                .defaultOffAll() // 关闭所有权限检查
                .allField()
                .forEach(mirrorField -> this.onMirrorFieldAnnotation(invObj, mirrorField));
    }

    protected <M, H> void onMirrorFieldAnnotation(Object invObj, MirrorField<M, H> mirrorField) {
        Class<M> declaringClass = (Class<M>) mirrorField.getTarget().getType();
        Annotation annotation = this.annotationHashMap.get(declaringClass);
        if (annotation != null) {
            mirrorField.doParameter((H) annotation);
            mirrorField.invoke(invObj, (ToValueFunction<H>) null);
        }
    }

    /**
     * 获得反射所操作的对象, 如构造器, 属性, 方法
     *
     * @return
     */
    public M getTarget() {
        return target;
    }


}
