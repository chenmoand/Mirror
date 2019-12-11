package com.brageast.mirror.interfaces;

import com.brageast.mirror.Mirror;

public interface MirrorType<T> {
    Mirror<T> invoke();

}
