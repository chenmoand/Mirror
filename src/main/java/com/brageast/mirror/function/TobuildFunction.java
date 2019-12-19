package com.brageast.mirror.function;

@FunctionalInterface
public interface TobuildFunction<M> {
    M build(Class<?> cls);
}
