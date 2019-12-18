package com.brageast.mirror.function;

/**
 * 异常功能接口
 */
@FunctionalInterface
public interface ThrowableFunction {
    /**
     * 对异常的操作
     *
     * @param throwable 异常
     */
    void doThrowable(Throwable throwable);

    /**
     * 判断这个接口是否为空
     * 如果为空则使用默认操作
     *
     * @param throwable         异常
     * @param throwableFunction 异常接口
     */
    static void isNull(Throwable throwable, ThrowableFunction throwableFunction) {
        if (throwable == null) return;
        if (throwableFunction == null) {
            throwable.printStackTrace();
        } else {
            throwableFunction.doThrowable(throwable);
        }
    }
}
