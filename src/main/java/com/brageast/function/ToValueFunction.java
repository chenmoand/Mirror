package com.brageast.function;

@FunctionalInterface
public interface ToValueFunction<E> {
    void toValue(E e);
}
