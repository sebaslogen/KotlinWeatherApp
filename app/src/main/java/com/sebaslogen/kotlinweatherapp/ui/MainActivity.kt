package com.sebaslogen.kotlinweatherapp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.sebaslogen.kotlinweatherapp.R
import com.sebaslogen.kotlinweatherapp.domain.commands.RequestForecastCommand
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val forecastList: RecyclerView = find(R.id.forecast_list)
        forecastList.layoutManager = LinearLayoutManager(this)

        loadData(forecastList)
    }

    private fun loadData(forecastList: RecyclerView) {
        doAsync({

        }, {
            val result = RequestForecastCommand("94043").execute()
            runOnUiThread {
                forecastList.adapter = ForecastListAdapter(result)
            }
        })
    }
}
