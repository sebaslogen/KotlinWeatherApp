package com.sebaslogen.kotlinweatherapp.domain.commands

public interface Command<T> {
    fun execute(): T
}