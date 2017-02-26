package com.sebaslogen.kotlinweatherapp.data.db

import com.sebaslogen.kotlinweatherapp.data.db.model.CityForecast
import com.sebaslogen.kotlinweatherapp.data.db.model.DayForecast
import com.sebaslogen.kotlinweatherapp.data.model.Forecast
import com.sebaslogen.kotlinweatherapp.data.model.ForecastList

class DbDataMapper {

    fun convertFromModel(forecast: ForecastList) = with(forecast) {
        val daily = dailyForecast.map { convertDayFromModel(id, it) }
        CityForecast(id, city, country, daily)
    }

    private fun convertDayFromModel(cityId: Long, forecast: Forecast) = with(forecast) {
        DayForecast(date, description, high, low, iconUrl, cityId)
    }

    fun convertToModel(forecast: CityForecast) = with(forecast) {
        val daily = dailyForecast.map { convertDayToModel(it) }
        ForecastList(_id, city, country, daily)
    }

    fun convertDayToModel(dayForecast: DayForecast) = with(dayForecast) {
        Forecast(_id, date, description, high, low, iconUrl)
    }
}