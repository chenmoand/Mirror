package com.brageast.kmirror.util

import java.lang.reflect.AccessibleObject
import java.lang.reflect.Method


object ReflectionUtil {

    @JvmStatic
    fun makeAccessible(type: AccessibleObject) {
        if (!type.isAccessible) {
            type.isAccessible = true
        }
    }

    fun findAllField(cls: Class<*>) {

    }

    @JvmStatic
    fun findConcreteMethodsOnInterfaces(cls: Class<*>): Set<Method> {
        val methods = hashSetOf<Method>()
        cls.interfaces
                .toNotNull()
                .filterNotNull()
                .forEach {
                    methods.addAll(
                            it.methods.toNotNull()
                    )
                }
        return methods
    }


    private fun <T : Any> Array<T>?.toNotNull(): Array<T> {
        if (this != null) {
            return this
        }
        throw IllegalArgumentException("array is null")
    }
}