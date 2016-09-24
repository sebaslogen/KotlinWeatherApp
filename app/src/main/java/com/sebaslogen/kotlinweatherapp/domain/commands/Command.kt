package com.sebaslogen.kotlinweatherapp.domain.commands

interface Command<T> {
    fun execute(): T
}