package com.zbistapp.weatherappforavito.domain.entityes

import com.google.gson.annotations.SerializedName

data class CurrentWeatherEntity(
    @SerializedName("weather") val weather: List<WeatherEntity>,
    @SerializedName("main") val main: MainEntity,
    @SerializedName("name") val name: String,
)
