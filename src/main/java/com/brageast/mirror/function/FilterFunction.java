package com.brageast.mirror.function;

@FunctionalInterface
public interface FilterFunction<E> {

    boolean doFilter(E e);

}
