package com.zbistapp.weatherappforavito.data

import android.location.Location
import com.zbistapp.weatherappforavito.BuildConfig
import com.zbistapp.weatherappforavito.domain.INetworkRepo
import com.zbistapp.weatherappforavito.domain.entityes.CurrentWeatherEntity
import com.zbistapp.weatherappforavito.domain.network.WeatherApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRepoImpl(
    private val weatherApi: WeatherApi
) : INetworkRepo {

    override suspend fun fetchCurrentWeather(
        location: Location,
        callback: (Result<CurrentWeatherEntity>) -> Unit
    ) {
        weatherApi.getCurrentWeather(
            location.latitude,
            location.longitude,
            BuildConfig.WEATHER_API_KEY
        ).enqueue(object : Callback<CurrentWeatherEntity> {
            override fun onResponse(
                call: Call<CurrentWeatherEntity>,
                response: Response<CurrentWeatherEntity>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    callback.invoke(Result.success(response.body()!!))
                } else {
                    callback.invoke(Result.failure(Throwable(response.code().toString())))
                }
            }

            override fun onFailure(call: Call<CurrentWeatherEntity>, t: Throwable) {
                callback.invoke(Result.failure(Throwable(t)))
            }
        })
    }
}