package com.sebaslogen.kotlinweatherapp.domain.commands

import com.sebaslogen.kotlinweatherapp.data.source.ForecastProvider
import com.sebaslogen.kotlinweatherapp.domain.model.Forecast

class RequestDayForecastCommand(
        val id: Long,
        val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)
}