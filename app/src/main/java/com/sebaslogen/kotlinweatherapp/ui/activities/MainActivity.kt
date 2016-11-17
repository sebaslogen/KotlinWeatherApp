package com.sebaslogen.kotlinweatherapp.ui.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.ImageView
import com.sebaslogen.kotlinweatherapp.R
import com.sebaslogen.kotlinweatherapp.domain.commands.RequestForecastCommand
import com.sebaslogen.kotlinweatherapp.domain.model.Forecast
import com.sebaslogen.kotlinweatherapp.ui.ForecastListAdapter
import com.sebaslogen.kotlinweatherapp.ui.utils.DelegatesExt
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.getStackTraceString


class MainActivity : AppCompatActivity(), ToolbarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    val zipCode: Long by DelegatesExt.preference(this, SettingsActivity.ZIP_CODE,
            SettingsActivity.DEFAULT_ZIP)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupTransitions()
        initToolbar()

        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)
    }

    /**
     * Draw shared element and other transitions only when this target view is ready to draw
     */
    private fun setupTransitions() {
        supportPostponeEnterTransition()
        forecastList.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                forecastList.viewTreeObserver.removeOnPreDrawListener(this)
                supportStartPostponedEnterTransition()
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loadData(forecastList)
    }

    private fun loadData(forecastList: RecyclerView) {
        doAsync({ throwable ->
            Log.e(javaClass.simpleName, "Error loading Forecast list:\n" + throwable.getStackTraceString())
        }, {
            val result = RequestForecastCommand(zipCode).execute()
            runOnUiThread {
                forecastList.adapter = ForecastListAdapter(result)
                { forecast: Forecast, imageView: ImageView ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val transitionActivityOptions = ActivityOptions
                                .makeSceneTransitionAnimation(this@MainActivity, imageView,
                                        getString(R.string.transition_weather_day) + forecast.id)
                        val i = Intent(this@MainActivity, DetailActivity::class.java)
                        i.putExtra(DetailActivity.ID, forecast.id)
                        i.putExtra(DetailActivity.CITY_NAME, result.city)
                        startActivity(i, transitionActivityOptions.toBundle())
                    }
                }
                toolbarTitle = "${result.city} (${result.country})"
            }
        })
    }
}
