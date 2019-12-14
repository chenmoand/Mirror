package com.brageast.mirror.interfaces;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;
import com.brageast.mirror.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.util.HashMap;

public abstract class AbstractMirrorType<T, M extends AccessibleObject, C> implements MirrorType<T, C> {
    // 实例对象的类
    protected T initObj;

    // 传进的Mirror
    protected Mirror<T> mirror;

    protected M target;

    protected HashMap<Class<? extends Annotation>, Annotation> annotationHashMap = new HashMap<>();

    public AbstractMirrorType() {

    }

    protected AbstractMirrorType(M target) {
        if (target instanceof Member) {
            Member member = (Member) target;
            Class<T> declaringClass = (Class<T>) member.getDeclaringClass();
            this.mirror = Mirror.just(declaringClass);
            this.initObj = ClassUtil.newInstance(declaringClass);
            this.target = target;
        }
    }


    /**
     * 判断是否有没有注解
     * 有返会注解,没用返回null
     *
     * @param annotation
     * @param <E>
     * @return
     */
    public <E extends Annotation> boolean hasAnntation(Class<E> annotation) {
        E declaredAnnotation = target.getDeclaredAnnotation(annotation);
        if(declaredAnnotation != null) {
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

    @Override
    public Mirror<T> invoke() {
        return this.invoke(null, null, null);
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

    public M getTarget() {
        return target;
    }
}
