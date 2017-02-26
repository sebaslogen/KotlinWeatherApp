package com.sebaslogen.kotlinweatherapp.data.remote

import com.sebaslogen.kotlinweatherapp.data.model.Forecast
import com.sebaslogen.kotlinweatherapp.data.model.ForecastList
import com.sebaslogen.kotlinweatherapp.data.source.ForecastDataSource

class ForecastServer(val dataMapper: ServerDataMapper = ServerDataMapper()) : ForecastDataSource {
    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val modelData = dataMapper.convertToModel(zipCode, result)
        return modelData
    }

    override fun requestDayForecast(id: Long): Forecast? {
        throw UnsupportedOperationException("not implemented")
    }
}