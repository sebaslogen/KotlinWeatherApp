package com.sebaslogen.kotlinweatherapp.data.source

import com.sebaslogen.kotlinweatherapp.data.model.Forecast
import com.sebaslogen.kotlinweatherapp.data.model.ForecastList

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long): Forecast?
}