package com.sebaslogen.kotlinweatherapp.domain.mappers

import com.sebaslogen.kotlinweatherapp.data.remote.ForecastResult
import com.sebaslogen.kotlinweatherapp.domain.model.ForecastList
import java.text.DateFormat
import java.util.*
import com.sebaslogen.kotlinweatherapp.data.remote.Forecast as RemoteForecast
import com.sebaslogen.kotlinweatherapp.domain.model.Forecast as ModelForecast

class ForecastDataMapper {

    fun convertFromDataModel(forecast: ForecastResult): ForecastList {
        return ForecastList(forecast.city.name, forecast.city.country, convertForecastListToDomain(forecast.list))
    }

    private fun convertForecastListToDomain(list: List<RemoteForecast>): List<ModelForecast> {
        return list.map { convertForecastItemToDomain(it) }
    }

    private fun convertForecastItemToDomain(forecast: RemoteForecast): ModelForecast {
        return ModelForecast(convertDate(forecast.dt), forecast.weather[0].description,
                forecast.temp.max.toInt(), forecast.temp.min.toInt())
    }

    private fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return df.format(date * 1000)
    }
}