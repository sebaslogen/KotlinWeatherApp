package com.sebaslogen.kotlinweatherapp.data.db

import android.content.Context
import com.sebaslogen.kotlinweatherapp.dependency.injection.ApplicationContext
import io.objectbox.BoxStore

class ForecastDbHelper(ctx: Context = ApplicationContext.get()) {
    var boxStore: BoxStore = MyObjectBox.builder().androidContext(ctx).build()

    companion object {
        val instance by lazy { ForecastDbHelper() }
    }

    fun getBoxStore() {
        boxStore
    }
}
