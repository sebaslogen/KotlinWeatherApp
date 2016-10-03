package com.sebaslogen.kotlinweatherapp.dependency.injection

import android.content.Context

class ApplicationContext {
    companion object {
        private lateinit var context: Context
        fun initialize(applicationContext: Context) {
            context = applicationContext
        }
        fun get() = context
    }
}