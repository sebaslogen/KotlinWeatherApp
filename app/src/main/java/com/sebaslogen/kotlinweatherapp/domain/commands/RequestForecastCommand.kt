package com.sebaslogen.kotlinweatherapp.domain.commands

import com.sebaslogen.kotlinweatherapp.data.source.ForecastProvider
import com.sebaslogen.kotlinweatherapp.data.model.ForecastList

class RequestForecastCommand( private val zipCode: Long,
        val forecastProvider: ForecastProvider = ForecastProvider()) : Command<ForecastList> {

    companion object {
        val DAYS = 7
    }

    override fun execute(): ForecastList {
        return forecastProvider.requestByZipCode(zipCode, DAYS)
    }
}