package com.sebaslogen.kotlinweatherapp

import android.app.Application
import com.sebaslogen.kotlinweatherapp.dependency.injection.ApplicationContext

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        ApplicationContext.initialize(this.applicationContext)
    }
}