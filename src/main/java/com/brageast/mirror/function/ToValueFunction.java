package com.brageast.mirror.function;

@FunctionalInterface
public interface ToValueFunction<E> {
    /**
     * 用于返回一个值
     *
     * @param e
     */
    void toValue(E e);

    /**
     * 判断这个接口是否为空
     * 如果为空则使用默认操作
     * @param obj
     * @param toValueFunction
     * @param <T>
     */
    static <T> void isNull(T obj, ToValueFunction<T> toValueFunction) {
        if (obj == null) return;
        if(toValueFunction != null) {
            toValueFunction.toValue(obj);
        }
    }
}
