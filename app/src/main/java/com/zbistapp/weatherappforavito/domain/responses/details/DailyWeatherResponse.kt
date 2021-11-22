package com.zbistapp.weatherappforavito.domain.responses.details

import com.google.gson.annotations.SerializedName
import com.zbistapp.weatherappforavito.domain.responses.WeatherResponse

data class DailyWeatherResponse(
    @SerializedName("dt") val date: Long,
    @SerializedName("temp") val temp: TempResponse,
    @SerializedName("weather") val weather: List<WeatherResponse>,
)
