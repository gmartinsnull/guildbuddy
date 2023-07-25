package com.gomart.guildbuddy.vo

import org.xml.sax.ErrorHandler

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
sealed class Resource<out T: Any> {
    class Success<out T : Any>(val data: T) : Resource<T>()

    class Error(
            private val exception: Throwable,
            val message: String = exception.message ?: "An unknown error occurred"
    ) : Resource<Nothing>()
}