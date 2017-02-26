package com.sebaslogen.kotlinweatherapp.data.remote

import com.sebaslogen.kotlinweatherapp.data.source.ForecastDataSource
import com.sebaslogen.kotlinweatherapp.domain.model.Forecast
import com.sebaslogen.kotlinweatherapp.domain.model.ForecastList

class ForecastServer(val dataMapper: ServerDataMapper = ServerDataMapper()) : ForecastDataSource {
    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val converted = dataMapper.convertToDomain(zipCode, result) // TODO: This should be converted to a generic DataModel without knowledge of the domain layer
        return converted
    }

    override fun requestDayForecast(id: Long): Forecast? {
        throw UnsupportedOperationException("not implemented")
    }
}