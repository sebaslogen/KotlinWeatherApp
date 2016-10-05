package com.sebaslogen.kotlinweatherapp.domain.commands

import com.sebaslogen.kotlinweatherapp.data.remote.ForecastRequest
import com.sebaslogen.kotlinweatherapp.domain.mappers.ForecastDataMapper
import com.sebaslogen.kotlinweatherapp.domain.model.ForecastList

class RequestForecastCommand(private val zipCode: Long) : Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(zipCode, forecastRequest.execute())
    }
}