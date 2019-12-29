package com.brageast.mirror.util;

import com.brageast.mirror.function.ToValueFunction;

/**
 * 比较大小的工具类
 *
 * @param <T>
 */
public final class CompareUtil<T extends Comparable<T>> {
    private T a;
    private T b;

    private CompareUtil() {

    }

    private CompareUtil(T a, T b) {
        this.a = a;
        this.b = b;
    }

    public static <E extends Comparable<E>> CompareUtil<E> then(E a, E b){
        return new CompareUtil(a, b);
    }

    public T big() {
        return a.compareTo(b) == 1 ? a : b;
    }
    public T small() {
        return a.compareTo(b) == -1 ? a : b;
    }
    public boolean isEqual() {
        return a.compareTo(b) == 0;
    }

    public CompareUtil<T> big(ToValueFunction<T> toValueFunction) {
        ToValueFunction.isNull(this.big(), toValueFunction);
        return this;
    }

    public CompareUtil<T> small(ToValueFunction<T> toValueFunction) {
        ToValueFunction.isNull(this.small(), toValueFunction);
        return this;
    }
}
