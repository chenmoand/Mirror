package com.brageast.mirror.util;

import com.brageast.mirror.function.RetrunValueFunction;

public final class ValueUtil<V> {
    private V valueA;
    private V valueB;

    private ValueUtil() {
    }

    private ValueUtil(V valueA, V valueB) {
        this.valueA = valueA;
        this.valueB = valueB;
    }

    public static <T> ValueUtil<T> of(T valueA, T valueB) {
        return new ValueUtil<>(valueA, valueB);
    }

    public static <E> ValueUtil<RetrunValueFunction<E>> of(RetrunValueFunction<E> valueA, RetrunValueFunction<E> valueB) {
        return new ValueUtil<>(valueA, valueB);
    }
    public static <E> ValueUtil<RetrunValueFunction<E>> of(RetrunValueFunction<E> valueA) {
        return new ValueUtil<>(valueA, null);
    }
    public static <T> ValueUtil<T> of(T valueA) {
        return new ValueUtil<>(valueA, null);
    }

    public V beforeIsNull(V defaultValue) {
        return valueA == null ? defaultValue : valueB;
    }

    public V beforeIsNull() {
        return beforeIsNull(valueA);
    }

    public V beforeNotNull(V defaultValue) {
        return valueA != null ? defaultValue : valueB;
    }

    public V beforeNotNull() {
        return beforeNotNull(valueA);
    }

    public V afterIsNull(V defaultValue) {
        return valueB == null ? defaultValue : valueA;
    }

    public V afterIsNull() {
        return afterIsNull(valueB);
    }

    public V afterNotNull(V defaultValue) {
        return valueB != null ? defaultValue : valueA;
    }

    public V afterNotNull() {
        return afterNotNull(valueB);
    }

    public V then(boolean bol) {
        return bol ? valueA : valueB;
    }

}
