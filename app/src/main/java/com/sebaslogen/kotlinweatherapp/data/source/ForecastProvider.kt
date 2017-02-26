package com.sebaslogen.kotlinweatherapp.data.source

import com.sebaslogen.kotlinweatherapp.data.db.ForecastDb
import com.sebaslogen.kotlinweatherapp.data.firstResult
import com.sebaslogen.kotlinweatherapp.data.remote.ForecastServer
import com.sebaslogen.kotlinweatherapp.domain.model.Forecast
import com.sebaslogen.kotlinweatherapp.domain.model.ForecastList

class ForecastProvider(val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {

    companion object {
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES by lazy { listOf(ForecastDb(), ForecastServer()) }
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList
            = requestToSources { requestSource(it, days, zipCode) }

    private fun requestSource(source: ForecastDataSource, days: Int, zipCode: Long): ForecastList? {
        val res = source.requestForecastByZipCode(zipCode, todayTimeSpan())
        return if (res != null && res.size() >= days) res else null
    }

    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T = sources.firstResult({ f(it) }, { storeInDb(it) })

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    fun requestForecast(id: Long): Forecast = requestToSources { it.requestDayForecast(id) }

    fun <T : Any> storeInDb(result: T) {
        sources.asSequence()
                .mapNotNull { if (it is ForecastDb && result is ForecastList) it else null }
                .forEach { it.saveForecast(result as ForecastList) }
    }
}