package com.sebaslogen.kotlinweatherapp.ui.activities

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.TextView
import com.sebaslogen.kotlinweatherapp.R
import com.sebaslogen.kotlinweatherapp.domain.commands.RequestDayForecastCommand
import com.sebaslogen.kotlinweatherapp.domain.model.Forecast
import com.sebaslogen.kotlinweatherapp.ui.utils.color
import com.sebaslogen.kotlinweatherapp.ui.utils.textColor
import com.sebaslogen.kotlinweatherapp.ui.utils.toDateString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import java.text.DateFormat

class DetailActivity : AppCompatActivity(), ToolbarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar)}

    companion object {
        val ID = "DetailActivity:id"
        val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail)
        val id = intent.getLongExtra(ID, -1)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            icon.transitionName = resources.getString(R.string.transition_weather_day) + id.toString()
        }
        initToolbar()

        toolbarTitle = intent.getStringExtra(CITY_NAME)
        enableHomeAsUp { onBackPressed() }

        doAsync({
            Log.e(javaClass.simpleName, "Error loading Day forecast")
        }, {
            val result = RequestDayForecastCommand(id).execute()
            runOnUiThread {
                bindForecast(result)
            }
        })
    }

    private fun bindForecast(forecast: Forecast) = with(forecast) {
        Picasso.with(ctx).load(iconUrl).into(icon)
        supportActionBar?.subtitle = date.toDateString(DateFormat.FULL)
        weatherDescription.text = description
        bindWeather(high to maxTemperature, low to minTemperature)
    }

    private fun bindWeather(vararg tempWithViews: Pair<Int, TextView>) = tempWithViews.forEach {
        val textView = it.second
        textView.text = "${it.first}º"
        textView.textColor = color(
                when (it.first) {
                    in -50..0 -> android.R.color.holo_red_dark
                    in 0..15 -> android.R.color.holo_orange_dark
                    else -> android.R.color.holo_green_dark
                })
    }
}