package com.sebaslogen.kotlinweatherapp.data.db

import com.sebaslogen.kotlinweatherapp.data.db.model.CityForecast
import com.sebaslogen.kotlinweatherapp.data.db.model.DayForecast
import com.sebaslogen.kotlinweatherapp.data.model.ForecastList
import com.sebaslogen.kotlinweatherapp.data.source.ForecastDataSource
import com.sebaslogen.kotlinweatherapp.data.toVarargArray
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.util.*

class ForecastDb(val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
                 val dataMapper: DbDataMapper = DbDataMapper()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {

        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"
        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                .parseList { DayForecast(HashMap(it)) }

        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt { CityForecast(HashMap(it), dailyForecast) }

        city?.let { dataMapper.convertToModel(city) }
    }

    override fun requestDayForecast(id: Long) = forecastDbHelper.use {
        val forecast = select(DayForecastTable.NAME).byId(id).
                parseOpt { DayForecast(HashMap(it)) }

        forecast?.let { dataMapper.convertDayToModel(forecast) }
    }

    fun saveForecastList(forecast: ForecastList): ForecastList {
        var ids: List<Long> = emptyList()
        forecastDbHelper.use {
            clear(CityForecastTable.NAME)
            clear(DayForecastTable.NAME)

            with(dataMapper.convertFromModel(forecast)) {
                insert(CityForecastTable.NAME, *map.toVarargArray())
                ids = dailyForecast.map { insert(DayForecastTable.NAME, *it.map.toVarargArray()) }
            }
        }
        if (forecast is ForecastList) return forecast.copy(dailyForecast = forecast.dailyForecast
                .mapIndexed { index, forecast -> forecast.copy(id = ids[index]) })
        return forecast
    }
}
