package com.sebaslogen.kotlinweatherapp.data

import com.sebaslogen.kotlinweatherapp.data.remote.ForecastServer
import java.util.*

fun <K, V : Any> MutableMap<K, V?>.toVarargArray(): Array<out Pair<K, V>> =
        map({ Pair(it.key, it.value!!) }).toTypedArray()


inline fun <T, R : Any> Iterable<T>.firstResult(predicate: (T) -> R?, resultPredicate: (R) -> Unit): R {
    for (element in this) {
        val result = predicate(element)
        if (result != null) {
            if (element is ForecastServer) resultPredicate(result)
            return result
        }
    }
    throw NoSuchElementException("No element matching predicate was found.")
}
