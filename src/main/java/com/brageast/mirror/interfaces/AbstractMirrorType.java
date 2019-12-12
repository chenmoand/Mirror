package com.brageast.mirror.interfaces;

import com.brageast.mirror.Mirror;

import java.lang.reflect.AccessibleObject;

public abstract class AbstractMirrorType<T, M extends AccessibleObject> implements MirrorType<T> {
    // 实例对象的类
    protected T initObj;
    // 传进的Mirror
    protected Mirror<T> mirror;
    protected Class<?>[] classTypes;
    protected Object[] objects;

    protected M target;


    public M getTarget() {
        return target;
    }
}
