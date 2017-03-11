package com.sebaslogen.kotlinweatherapp.data.source

import com.sebaslogen.kotlinweatherapp.data.db.ForecastDb
import com.sebaslogen.kotlinweatherapp.data.firstResult
import com.sebaslogen.kotlinweatherapp.data.model.Forecast
import com.sebaslogen.kotlinweatherapp.data.model.ForecastList
import com.sebaslogen.kotlinweatherapp.data.remote.ForecastServer

class ForecastProvider(val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {

    companion object {
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES by lazy { listOf(ForecastDb(), ForecastServer()) }
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList {
        val forecastList = requestToSources { requestSource(it, days, zipCode) }
        if (forecastList.dailyForecast.any { it.id == -1L }) {
            return (sources.first { it is ForecastDb } as ForecastDb).saveForecastList(forecastList)
        } else {
            return forecastList
        }
    }

    fun requestForecast(id: Long): Forecast = requestToSources { it.requestDayForecast(id) }

    private fun requestSource(source: ForecastDataSource, days: Int, zipCode: Long): ForecastList? {
        val res = source.requestForecastByZipCode(zipCode, todayTimeSpan())
        return if (res != null && res.size() >= days) res else null
    }

    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T =
            sources.firstResult({ f(it) })

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS
}