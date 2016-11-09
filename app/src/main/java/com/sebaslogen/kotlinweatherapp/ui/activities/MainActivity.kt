package com.sebaslogen.kotlinweatherapp.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import com.sebaslogen.kotlinweatherapp.R
import com.sebaslogen.kotlinweatherapp.domain.commands.RequestForecastCommand
import com.sebaslogen.kotlinweatherapp.ui.ForecastListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), ToolbarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)

        loadData(forecastList)
    }

    private fun loadData(forecastList: RecyclerView) {
        doAsync({
            Log.e(javaClass.simpleName, "Error loading Forecast list")
        }, {
            val result = RequestForecastCommand(94043).execute()
            runOnUiThread {
                forecastList.adapter = ForecastListAdapter(result) {
                    startActivity<DetailActivity>(
                            DetailActivity.ID to it.id,
                            DetailActivity.CITY_NAME to result.city)
                }
                toolbarTitle = "${result.city} (${result.country})"
            }
        })
    }
}
