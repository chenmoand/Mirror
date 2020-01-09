package com.brageast.KMirror

import kotlin.reflect.KClass

class Mirror<T: Any> {
    /**
     * 操作的实例
     */
    private var instance: T? = null
        get() = instance
    /**
     * 实例的对象的类
     */
    private val instanceClass: Class<T>

    constructor(instanceClass: Class<T>) {
        this.instanceClass = instanceClass
    }
    constructor(instance: T) {
        this.instance = instance
        this.instanceClass = instance.javaClass
    }

    companion object {
        @JvmStatic
        fun <E: Any> of(instance: E): Mirror<E> = Mirror(instance)
        @JvmStatic
        fun <E: Any> of(instanceClass: Class<E>): Mirror<E> = Mirror(instanceClass)

        fun <E: Any> of(instanceKClass: KClass<E>): Mirror<E> = Mirror(instanceKClass.java)

        inline fun <reified E: Any> of(): Mirror<E> = Mirror(instanceClass = E::class.java)


    }

}