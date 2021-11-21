package com.zbistapp.weatherappforavito.domain.responses.current

import com.google.gson.annotations.SerializedName

data class MainResponse(
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp") val temp: Double,
    @SerializedName("temp_max") val tempMax: Double,
    @SerializedName("temp_min") val tempMin: Double
)
