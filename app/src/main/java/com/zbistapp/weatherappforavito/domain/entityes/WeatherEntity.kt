package com.zbistapp.weatherappforavito.domain.entityes

import com.google.gson.annotations.SerializedName

data class WeatherEntity(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)
