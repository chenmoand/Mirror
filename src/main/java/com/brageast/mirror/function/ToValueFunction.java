package com.brageast.mirror.function;

@FunctionalInterface
public interface ToValueFunction<E> {
    void toValue(E e);
}
