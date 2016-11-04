package com.sebaslogen.kotlinweatherapp

import android.app.Application
import com.sebaslogen.kotlinweatherapp.dependency.injection.ApplicationContext
import com.sebaslogen.kotlinweatherapp.ui.utils.DelegatesExt

class App: Application() {

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        ApplicationContext.initialize(this.applicationContext)
        instance = this
    }
}