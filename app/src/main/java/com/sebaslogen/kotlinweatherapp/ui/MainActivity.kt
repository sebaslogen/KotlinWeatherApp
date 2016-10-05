package com.sebaslogen.kotlinweatherapp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.sebaslogen.kotlinweatherapp.R
import com.sebaslogen.kotlinweatherapp.domain.commands.RequestForecastCommand
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        forecastList.layoutManager = LinearLayoutManager(this)
        loadData(forecastList)
    }

    private fun loadData(forecastList: RecyclerView) {
        doAsync({
            Log.e(javaClass.simpleName, "Error loading Forecast list")
        }, {
            val result = RequestForecastCommand(94043).execute()
            runOnUiThread {
                forecastList.adapter = ForecastListAdapter(result) { toast(it.description) }
            }
        })
    }
}
