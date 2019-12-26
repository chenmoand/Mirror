package com.brageast.mirror.interfaces;

import com.brageast.mirror.Mirror;
import com.brageast.mirror.function.InvokeFunction;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.function.ToValueFunction;

public interface MirrorOperation<T, C> {

    default Mirror<T> invoke() {
        return this.invoke(null, (ThrowableFunction) null, null);
    }

    default Mirror<T> invoke(Object invObj, ToValueFunction<C> toValueFunction) {
        return this.invoke(invObj, null, toValueFunction);
    }

    default Mirror<T> invoke(ToValueFunction<C> toValueFunction) {
        return this.invoke(null, null, toValueFunction);
    }

    Mirror<T> invoke(Object invObj, ThrowableFunction throwableFunction, ToValueFunction<C> toValueFunction);


    default Mirror<T> invoke(InvokeFunction invokeFunction) {
        return this.invoke(null, invokeFunction);
    }

    default Mirror<T> invoke(Object invObj, InvokeFunction invokeFunction) {
        return this.invoke(invObj, invokeFunction, null);
    }

    Mirror<T> invoke(Object invObj, InvokeFunction invokeFunction, ThrowableFunction throwableFunction);

}
