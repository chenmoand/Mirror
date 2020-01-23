package com.brageast.kmirror

import com.brageast.kmirror.interfaces.MirrorLink

class MirrorConstructor<T : Any> : MirrorLink<T> {


    constructor(mirror: Mirror<T>, vararg parameters: Any) : super(mirror, *parameters)

/*    constructor(constructor: Constructor<T>) {
        this.mirror = Mirror.of(constructor.declaringClass)
//        TODO("未完成")
    }*/


}