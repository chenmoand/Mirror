package com.brageast.kmirror.interfaces

import com.brageast.kmirror.Mirror

abstract class MirrorLink<T: Any> {
    val mirror: Mirror<T>

    lateinit var parameters: Array<out Any>

    constructor(mirror: Mirror<T>, vararg parameters: Any) {
        this.mirror = mirror
        this.parameters = parameters
    }
    fun and(): Mirror<T> {
        return mirror
    }
}
