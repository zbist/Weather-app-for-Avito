package com.zbistapp.weatherappforavito.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zbistapp.weatherappforavito.BuildConfig
import com.zbistapp.weatherappforavito.R
import com.zbistapp.weatherappforavito.domain.responses.details.DailyWeatherResponse
import com.zbistapp.weatherappforavito.utils.Converter

class DailyWeatherAdapter : RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherHolder>() {

    private val converter = Converter()
    var listOfDailyWeather = emptyList<DailyWeatherResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.daily_weather_item, parent, false)
        return DailyWeatherHolder(view)
    }

    override fun onBindViewHolder(holder: DailyWeatherHolder, position: Int) {
        with(holder) {
            val highTemperature =
                "${converter.kelvinToCelsius(listOfDailyWeather[position].temp.maxTemp)}°/"
            highTemp.text = highTemperature
            val lowTemperature =
                "${converter.kelvinToCelsius(listOfDailyWeather[position].temp.minTemp)}°"
            lowTemp.text = lowTemperature
            date.text = converter.timeToDate(listOfDailyWeather[position].date)
            Glide.with(img)
                .load(
                    "${BuildConfig.IMG_URL}${listOfDailyWeather[position].weather[0].icon}.png"
                ).into(img)
        }
    }

    override fun getItemCount(): Int = listOfDailyWeather.size

    inner class DailyWeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.date_text_view)
        val highTemp: TextView = itemView.findViewById(R.id.high_temperature_text_view)
        val lowTemp: TextView = itemView.findViewById(R.id.low_temperature_text_view)
        val img: ImageView = itemView.findViewById(R.id.picture_image_view)
    }
}