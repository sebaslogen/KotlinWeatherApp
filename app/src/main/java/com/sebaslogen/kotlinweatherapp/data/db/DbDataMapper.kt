package com.sebaslogen.kotlinweatherapp.data.db

import com.sebaslogen.kotlinweatherapp.data.db.model.CityForecast
import com.sebaslogen.kotlinweatherapp.data.db.model.DayForecast
import com.sebaslogen.kotlinweatherapp.domain.model.Forecast
import com.sebaslogen.kotlinweatherapp.domain.model.ForecastList

class DbDataMapper { // TODO: This should convert from/to DB and generic data model

    fun convertFromDomain(forecast: ForecastList) = with(forecast) {
        val daily = dailyForecast.map { convertDayFromDomain(id, it) }
        CityForecast(id, city, country, daily)
    }

    private fun convertDayFromDomain(cityId: Long, forecast: Forecast) = with(forecast) {
        DayForecast(date, description, high, low, iconUrl, cityId)
    }

    fun convertToDomain(forecast: CityForecast) = with(forecast) {
        val daily = dailyForecast.map { convertDayToDomain(it) }
        ForecastList(_id, city, country, daily)
    }

    fun convertDayToDomain(dayForecast: DayForecast) = with(dayForecast) {
        Forecast(_id, date, description, high, low, iconUrl)
    }
}