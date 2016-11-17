package com.sebaslogen.kotlinweatherapp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.sebaslogen.kotlinweatherapp.R
import com.sebaslogen.kotlinweatherapp.domain.model.Forecast
import com.sebaslogen.kotlinweatherapp.domain.model.ForecastList
import com.sebaslogen.kotlinweatherapp.ui.utils.ctx
import com.sebaslogen.kotlinweatherapp.ui.utils.toDateString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastListAdapter(private val items: ForecastList,
                          private val itemClick: (Forecast, ImageView) -> Unit) :
        RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(items[position])
    }

    override fun getItemCount() = items.size()

    class ViewHolder(view: View, private val itemClick: (Forecast, ImageView) -> Unit) :
            RecyclerView.ViewHolder(view) {

        fun bindForecast(forecast: Forecast) {
            with(forecast) { // Analog to forecast.apply {  }
                Picasso.with(itemView.ctx).load(iconUrl).into(itemView.weatherIcon)
                itemView.date.text = date.toDateString()
                itemView.description.text = description
                itemView.maxTemperature.text = "${high}º"
                itemView.minTemperature.text = "${low}º"
                itemView.setOnClickListener { itemClick(this, itemView.weatherIcon) }
            }
        }
    }
}