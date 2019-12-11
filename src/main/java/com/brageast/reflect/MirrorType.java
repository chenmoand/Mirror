package com.brageast.reflect;

import com.brageast.Mirror;

public interface MirrorType<T> {
    Mirror<T> invoke();

}
