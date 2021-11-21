package com.zbistapp.weatherappforavito.domain.network

import com.zbistapp.weatherappforavito.domain.entityes.CurrentWeatherEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): Call<CurrentWeatherEntity>
}