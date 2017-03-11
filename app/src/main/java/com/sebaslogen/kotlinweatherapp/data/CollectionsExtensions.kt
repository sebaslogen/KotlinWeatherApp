package com.sebaslogen.kotlinweatherapp.data

fun <K, V : Any> MutableMap<K, V?>.toVarargArray(): Array<out Pair<K, V>> =
        map({ Pair(it.key, it.value!!) }).toTypedArray()


inline fun <T, R : Any> Iterable<T>.firstResult(crossinline predicate: (T) -> R?): R =
        this.asSequence().mapNotNull { predicate(it) }.first()
