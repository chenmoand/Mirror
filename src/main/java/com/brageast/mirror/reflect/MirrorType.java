package com.brageast.mirror.reflect;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.FilterFunction;
import com.brageast.mirror.function.ToValueFunction;

import java.lang.annotation.Annotation;

public class MirrorType<T> {

    private Mirror<T> mirror;
    private Class<T> tClass;

    public MirrorType(Mirror<T> mirror, Class<T> tClass) {
        this.mirror = mirror;
        this.tClass = tClass;
    }

    public Mirror<T> and() {
        return mirror;
    }

    public Class<T> getTypeClass() {
        return tClass;
    }

    public MirrorType<T> doClassType(FilterFunction<Class<T>> filterFunction, ToValueFunction<Mirror<T>> toValueFunction) {
        if (
            filterFunction != null &&
            filterFunction.doFilter(tClass)
        ) {
            if (toValueFunction != null) toValueFunction.toValue(mirror);
        }
        return this;
    }

    public MirrorType<T> isInterface(ToValueFunction<Mirror<T>> toValueFunction) {
        this.doClassType(Class::isInterface, toValueFunction);
        return this;
    }

    public MirrorType<T> isAnnotation(ToValueFunction<Mirror<T>> toValueFunction) {
        this.doClassType(Class::isAnnotation, toValueFunction);
        return this;
    }

    public MirrorType<T> isEnum(ToValueFunction<Mirror<T>> toValueFunction) {
        this.doClassType(Class::isEnum, toValueFunction);
        return this;
    }

    public MirrorType<T> isMemberClass(ToValueFunction<Mirror<T>> toValueFunction) {
        this.doClassType(Class::isMemberClass, toValueFunction);
        return this;
    }

    public MirrorType<T> isPrimitive(ToValueFunction<Mirror<T>> toValueFunction) {
        this.doClassType(Class::isPrimitive, toValueFunction);
        return this;
    }

    public MirrorType<T> isSynthetic(ToValueFunction<Mirror<T>> toValueFunction) {
        this.doClassType(Class::isSynthetic, toValueFunction);
        return this;
    }

    public MirrorType<T> isAnonymousClass(ToValueFunction<Mirror<T>> toValueFunction) {
        this.doClassType(Class::isAnonymousClass, toValueFunction);
        return this;
    }

    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        return this.getTypeClass().getDeclaredAnnotation(cls);
    }

    public <A extends Annotation> A getAnnotation(A annotation) {
        return (A) this.getTypeClass().getDeclaredAnnotation(annotation.getClass());
    }

    public <A extends Annotation> MirrorType<T> doAnnotation(Class<A> cls, ToValueFunction<A> toValueFunction) {
        ToValueFunction.isNull(this.getAnnotation(cls), toValueFunction);
        return this;
    }
    public <A extends Annotation> MirrorType<T> doAnnotation(A annotation, ToValueFunction<A> toValueFunction) {
        ToValueFunction.isNull(this.getAnnotation(annotation), toValueFunction);
        return this;
    }

}
