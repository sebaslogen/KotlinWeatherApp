package com.sebaslogen.kotlinweatherapp.domain.commands

interface Command<out T> {
    fun execute(): T
}