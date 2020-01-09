package com.brageast.kmirror

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
        private const val import: String = "com.brageast.kmirror.Mirror.Companion.toMirror"
        private const val message: String = "please use toMirror()"

        @JvmStatic
        @Deprecated(
                message = message,
                replaceWith = ReplaceWith("instance.toMirror()", import)
        )
        fun <E: Any> of(instance: E): Mirror<E> = Mirror(instance)
        @JvmStatic
        @Deprecated(
                message = message,
                replaceWith = ReplaceWith("instanceClass.toMirror()", import)
        )
        fun <E: Any> of(instanceClass: Class<E>): Mirror<E> = Mirror(instanceClass)
        @Deprecated(
                message = message,
                replaceWith = ReplaceWith("instanceKClass.toMirror()", import)
        )
        fun <E: Any> of(instanceKClass: KClass<E>): Mirror<E> = Mirror(instanceKClass.java)
        inline fun <reified E: Any> of(): Mirror<E> = Mirror(instanceClass = E::class.java)


        fun <E: Any> E.toMirror() = Mirror(this)

        fun <E: Any> Class<E>.toMirror() = Mirror(this)

        fun <E: Any> KClass<E>.toMirror() = Mirror(instanceClass = this.java)
    }

}