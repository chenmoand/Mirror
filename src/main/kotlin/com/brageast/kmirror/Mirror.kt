package com.brageast.kmirror

import kotlin.reflect.KClass

class Mirror<T : Any>(
        /**
         * 实例的对象的类
         */
        private val instanceClass: Class<T>
) {

    /**
     * 操作的实例
     */
    lateinit var instance: T
        private set

    constructor(instance: T) : this(instance.javaClass) {
        this.instance = instance
    }


    private fun Back(callback: () -> Unit = CallBack.emptyCallBack): Mirror<T> {
        callback()
        return this@Mirror
    }


    companion object {
        @JvmStatic
        fun <E : Any> of(instance: E): Mirror<E> = Mirror(instance)

        @JvmStatic
        fun <E : Any> of(instanceClass: Class<E>): Mirror<E> = Mirror(instanceClass)

        fun <E : Any> of(instanceKClass: KClass<E>): Mirror<E> = Mirror(instanceKClass.java)

        @JvmStatic
        fun of(url: String): Mirror<out Any> = Mirror(Class.forName(url))

        inline fun <reified E : Any> of(): Mirror<E> = Mirror(instanceClass = E::class.java)
    }

}