package com.sebaslogen.kotlinweatherapp.data.source

import com.sebaslogen.kotlinweatherapp.domain.model.ForecastList

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?
}