package com.brageast.mirror.reflect;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;
import com.brageast.mirror.interfaces.AbstractMirrorType;
import com.brageast.mirror.interfaces.MirrorEntity;

import java.lang.reflect.Constructor;

public class MirrorConstructor<T> extends AbstractMirrorType<T, Constructor<T>, T> {


    public MirrorConstructor(Constructor<T> target) {
        super(target);
    }

    @Override
    public Mirror<T> invoke(Object invObj, ThrowableFunction throwableFunction, ToValueFunction<T> toValueFunction) {
        return null;
    }


    @Override
    public Mirror<T> invoke(Object invObj, MirrorEntity mirrorEntity) {
        return null;
    }
}
