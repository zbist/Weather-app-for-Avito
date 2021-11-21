package com.zbistapp.weatherappforavito.domain.responses.current

import com.google.gson.annotations.SerializedName
import com.zbistapp.weatherappforavito.domain.responses.WeatherResponse

data class CurrentWeatherResponse(
    @SerializedName("weather") val weather: List<WeatherResponse>,
    @SerializedName("main") val main: MainResponse,
    @SerializedName("name") val name: String,
    @SerializedName("dt") val date: Long,
)
