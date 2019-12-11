package com.brageast.mirror.util;

public class Null {
    private Class<?> aClass;
    private Null(Class<?> aClass) {
        this.aClass = aClass;
    }
    public static Null is(Class<?> aClass) {
        return new Null(aClass);
    }

    public Class<?> getTypeClass() {
        return aClass;
    }
}
