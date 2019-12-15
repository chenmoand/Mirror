package com.brageast.mirror.interfaces;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;

public interface MirrorType<T, C> {
    Mirror<T> invoke();

    Mirror<T> invoke(Object invObj, ToValueFunction<C> toValueFunction);

    Mirror<T> invoke(ToValueFunction<C> toValueFunction);

    Mirror<T> invoke(MirrorEntity mirrorEntity);

    Mirror<T> invoke(Object invObj, MirrorEntity mirrorEntity);

    Mirror<T> invoke(Object invObj, MirrorEntity mirrorEntity, ThrowableFunction throwableFunction);
}
