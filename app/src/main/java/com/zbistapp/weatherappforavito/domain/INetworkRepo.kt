package com.zbistapp.weatherappforavito.domain

import android.location.Location
import com.zbistapp.weatherappforavito.domain.responses.current.CurrentWeatherResponse
import com.zbistapp.weatherappforavito.domain.responses.details.DetailedWeatherResponse

interface INetworkRepo {

    suspend fun fetchCurrentWeather(
        location: Location,
        callback: (Result<CurrentWeatherResponse>) -> Unit
    )

    suspend fun fetchDetailedWeather(
        location: Location,
        callback: (Result<DetailedWeatherResponse>) -> Unit
    )
}