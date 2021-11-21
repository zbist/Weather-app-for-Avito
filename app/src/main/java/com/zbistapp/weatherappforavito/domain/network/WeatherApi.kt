package com.zbistapp.weatherappforavito.domain.network

import com.zbistapp.weatherappforavito.domain.responses.current.CurrentWeatherResponse
import com.zbistapp.weatherappforavito.domain.responses.details.DetailedWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): Call<CurrentWeatherResponse>

    @GET("onecall")
    fun getDetailedWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("exclude") exclude: String = "current,minutely",
    ): Call<DetailedWeatherResponse>
}