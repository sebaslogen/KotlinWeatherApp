package com.sebaslogen.kotlinweatherapp.data.remote

import com.sebaslogen.kotlinweatherapp.data.db.ForecastDb
import com.sebaslogen.kotlinweatherapp.data.source.ForecastDataSource
import com.sebaslogen.kotlinweatherapp.domain.model.ForecastList

class ForecastServer(val dataMapper: ServerDataMapper = ServerDataMapper(),
                     val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val converted = dataMapper.convertToDomain(zipCode, result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }
}