package com.sebaslogen.kotlinweatherapp.domain.commands

import com.sebaslogen.kotlinweatherapp.data.model.Forecast
import com.sebaslogen.kotlinweatherapp.data.source.ForecastProvider

class RequestDayForecastCommand(
        val id: Long,
        val forecastProvider: ForecastProvider = ForecastProvider()) : Command<Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)
}