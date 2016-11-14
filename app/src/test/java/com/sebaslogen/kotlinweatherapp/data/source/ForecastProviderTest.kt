package com.sebaslogen.kotlinweatherapp.data.source

import com.sebaslogen.kotlinweatherapp.domain.model.Forecast
import org.junit.Assert.assertNotNull
import org.junit.Test
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
}