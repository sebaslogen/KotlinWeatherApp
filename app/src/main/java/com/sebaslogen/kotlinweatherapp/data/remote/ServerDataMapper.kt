package com.sebaslogen.kotlinweatherapp.data.remote

import com.sebaslogen.kotlinweatherapp.data.remote.model.Forecast
import com.sebaslogen.kotlinweatherapp.data.remote.model.ForecastResult
import com.sebaslogen.kotlinweatherapp.domain.model.ForecastList
import com.sebaslogen.kotlinweatherapp.domain.model.Forecast as ModelForecast

class ServerDataMapper { // TODO: This should convert from/to sever and generic data model

    fun convertToDomain(zipCode: Long, forecast: ForecastResult) = with(forecast) {
        ForecastList(zipCode, city.name, city.country, convertForecastListToDomain(list))
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        return list.map { convertForecastItemToDomain(it) }
    }

    private fun convertForecastItemToDomain(forecast: Forecast) = with(forecast) {
        ModelForecast(-1, dt * 1000, weather[0].description, temp.max.toInt(), temp.min.toInt(),
                generateIconUrl(weather[0].icon))
    }

    private fun generateIconUrl(iconCode: String) = "http://openweathermap.org/img/w/$iconCode.png"
}