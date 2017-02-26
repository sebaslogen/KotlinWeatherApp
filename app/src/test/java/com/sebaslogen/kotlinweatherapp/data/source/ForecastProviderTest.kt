package com.sebaslogen.kotlinweatherapp.data.source

import com.sebaslogen.kotlinweatherapp.domain.model.Forecast
import com.sebaslogen.kotlinweatherapp.domain.model.ForecastList
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class ForecastProviderTest {

    @Test fun testDataSourceReturnsValue() {
        // Given
        val dataSource = mock(ForecastDataSource::class.java)
        `when`(dataSource.requestDayForecast(0)).then {
            Forecast(0, 0, "desc", 20, 0, "url")
        }
        // When
        val provider = ForecastProvider(listOf(dataSource))
        // Then
        assertNotNull(provider.requestForecast(0))
    }

    @Test fun emptyDatabaseReturnsServerValue() {
        // Given
        val db = mock(ForecastDataSource::class.java)
        val server = mock(ForecastDataSource::class.java)
        `when`(server.requestForecastByZipCode(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .then {
                    ForecastList(0, "city", "country", listOf())
                }
        // When
        val provider = ForecastProvider(listOf(db, server))
        // Then
        assertNotNull(provider.requestByZipCode(0, 0))
    }
}