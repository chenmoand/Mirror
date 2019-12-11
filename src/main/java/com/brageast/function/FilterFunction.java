package com.brageast.function;

@FunctionalInterface
public interface FilterFunction<E> {

    boolean doFilter(E e);

}
