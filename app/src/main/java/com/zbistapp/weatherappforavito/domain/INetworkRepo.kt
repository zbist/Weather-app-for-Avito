package com.zbistapp.weatherappforavito.domain

import android.location.Location
import com.zbistapp.weatherappforavito.domain.entityes.CurrentWeatherEntity

interface INetworkRepo {

    suspend fun fetchCurrentWeather(
        location: Location,
        callback: (Result<CurrentWeatherEntity>) -> Unit
    )
}