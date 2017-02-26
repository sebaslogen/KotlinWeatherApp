package com.sebaslogen.kotlinweatherapp.data.remote

import com.sebaslogen.kotlinweatherapp.data.model.ForecastList
import com.sebaslogen.kotlinweatherapp.data.remote.model.Forecast
import com.sebaslogen.kotlinweatherapp.data.remote.model.ForecastResult
import com.sebaslogen.kotlinweatherapp.data.model.Forecast as ModelForecast

class ServerDataMapper {

    fun convertToModel(zipCode: Long, forecast: ForecastResult) = with(forecast) {
        ForecastList(zipCode, city.name, city.country, convertForecastListToModel(list))
    }

    private fun convertForecastListToModel(list: List<Forecast>): List<ModelForecast> {
        return list.map { convertForecastItemToModel(it) }
    }

    private fun convertForecastItemToModel(forecast: Forecast) = with(forecast) {
        ModelForecast(-1, dt * 1000, weather[0].description, temp.max.toInt(), temp.min.toInt(),
                generateIconUrl(weather[0].icon))
    }

    private fun generateIconUrl(iconCode: String) = "http://openweathermap.org/img/w/$iconCode.png"
}