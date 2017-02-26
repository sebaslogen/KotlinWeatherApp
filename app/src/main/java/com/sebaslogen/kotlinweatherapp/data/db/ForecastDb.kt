package com.sebaslogen.kotlinweatherapp.data.db

import com.sebaslogen.kotlinweatherapp.data.db.model.CityForecast
import com.sebaslogen.kotlinweatherapp.data.db.model.DayForecast
import com.sebaslogen.kotlinweatherapp.data.source.ForecastDataSource
import com.sebaslogen.kotlinweatherapp.data.toVarargArray
import com.sebaslogen.kotlinweatherapp.data.model.ForecastList
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

    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {

        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        with(dataMapper.convertFromModel(forecast)) {
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach { insert(DayForecastTable.NAME, *it.map.toVarargArray()) }
        }
    }
}
