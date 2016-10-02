package com.sebaslogen.kotlinweatherapp.domain.commands

import com.sebaslogen.kotlinweatherapp.data.remote.ForecastRequest
import com.sebaslogen.kotlinweatherapp.domain.mappers.ForecastDataMapper
import com.sebaslogen.kotlinweatherapp.domain.model.ForecastList

class RequestForecastCommand(private val zipCode: String) : Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        val forecastResult = forecastRequest.execute()
        return ForecastDataMapper().convertFromDataModel(forecastResult)
    }
}