package com.brageast.mirror.function;

/**
 * 过滤器功能接口
 *
 * @param <E>
 */
@FunctionalInterface
public interface FilterFunction<E> {

    boolean doFilter(E e);

}
