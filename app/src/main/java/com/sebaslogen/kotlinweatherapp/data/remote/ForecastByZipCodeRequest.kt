package com.sebaslogen.kotlinweatherapp.data.remote

import com.sebaslogen.kotlinweatherapp.data.remote.model.ForecastResult
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.net.URL

class ForecastByZipCodeRequest(val zipCode: Long,
                               val jsonAdapter: JsonAdapter<ForecastResult> = Moshi.Builder()
                                       .build().adapter(ForecastResult::class.java)) {

    companion object {
        private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private val URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7"
        private val COMPLETE_URL = "${URL}&APPID=${APP_ID}&q="
    }

    fun execute(): ForecastResult {
        val forecastJsonStr = URL(COMPLETE_URL + zipCode).readText()
        return jsonAdapter.fromJson(forecastJsonStr)
    }
}