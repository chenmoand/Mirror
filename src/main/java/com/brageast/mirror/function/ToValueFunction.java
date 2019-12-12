package com.brageast.mirror.function;

@FunctionalInterface
public interface ToValueFunction<E> {
    void toValue(E e);
    static <T> void isNull(T obj, ToValueFunction<T> toValueFunction) {
        if (obj == null) return;
        if(toValueFunction != null) {
            toValueFunction.toValue(obj);
        }
    }
}
