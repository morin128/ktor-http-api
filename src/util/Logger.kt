package com.example.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

inline fun <reified T : Any> T.logger(): Lazy<Logger> = lazy {
    getLogger(getClassForLogger(T::class.java).name)
}

fun <T : Any> getClassForLogger(javaClass: Class<T>): Class<*> {
    return javaClass.enclosingClass?.takeIf {
        it.kotlin.isCompanion
    } ?: javaClass
}