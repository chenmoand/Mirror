package com.brageast.mirror.function;

@FunctionalInterface
public interface ThrowableFunction {
    void doThrowable(Throwable throwable);

    static void isNull(Throwable throwable, ThrowableFunction throwableFunction) {
        if (throwable == null) return;
        if(throwableFunction == null) {
            throwable.printStackTrace();
        } else {
            throwableFunction.doThrowable(throwable);
        }
    }
}
