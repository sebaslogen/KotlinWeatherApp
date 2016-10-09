package com.sebaslogen.kotlinweatherapp.domain.mappers

import com.sebaslogen.kotlinweatherapp.data.remote.ForecastResult
import com.sebaslogen.kotlinweatherapp.domain.model.ForecastList
import com.sebaslogen.kotlinweatherapp.data.remote.Forecast as RemoteForecast
import com.sebaslogen.kotlinweatherapp.domain.model.Forecast as ModelForecast

class ForecastDataMapper {

    fun convertFromDataModel(zipCode: Long, forecast: ForecastResult) = with(forecast) {
        ForecastList(zipCode, city.name, city.country, convertForecastListToDomain(list))
    }

    private fun convertForecastListToDomain(list: List<RemoteForecast>): List<ModelForecast> {
        return list.map { convertForecastItemToDomain(it) }
    }

    private fun convertForecastItemToDomain(
            forecast: com.sebaslogen.kotlinweatherapp.data.remote.Forecast) = with(forecast) {
        ModelForecast(-1, dt * 1000, weather[0].description, temp.max.toInt(), temp.min.toInt(),
                generateIconUrl(weather[0].icon))
    }

    private fun generateIconUrl(iconCode: String) = "http://openweathermap.org/img/w/$iconCode.png"
}