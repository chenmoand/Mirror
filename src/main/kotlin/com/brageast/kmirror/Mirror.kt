package com.brageast.kmirror

import kotlin.reflect.KClass

class Mirror<T : Any> {
    /**
     * 操作的实例
     */
    var instance: T? = null
        private set

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

    var checkPermissions: Boolean = true
        @JvmName("isCheckPermissions") get
        private set

    fun closeCheck(): Mirror<T> = Back {
        checkPermissions = false
    }

    var useDeclared: Boolean = true
        @JvmName("isUseDeclared") get
        private set

    fun notUserDeclared(): Mirror<T> = Back {
        useDeclared = false
    }


    private fun Any.Back(): Mirror<T> = this@Mirror
    private fun Any.Back(callback: () -> Unit): Mirror<T> = callback().Back()

    companion object {
        @JvmStatic
        fun <E : Any> of(instance: E): Mirror<E> = Mirror(instance)

        @JvmStatic
        fun <E : Any> of(instanceClass: Class<E>): Mirror<E> = Mirror(instanceClass)

        fun <E : Any> of(instanceKClass: KClass<E>): Mirror<E> = Mirror(instanceKClass.java)

        @JvmStatic
        @JvmOverloads
        fun of(url: String, throwableCallBack: (Throwable) -> Unit = CallBack.throwableCallBack): Mirror<out Any> {
            var mirror: Mirror<out Any>? = null
            try {
                mirror = Mirror(Class.forName(url))
            } catch (e: ClassNotFoundException) {
                throwableCallBack(e)
            }
            return mirror!!
        }

        inline fun <reified E : Any> of(): Mirror<E> = Mirror(instanceClass = E::class.java)
    }

}