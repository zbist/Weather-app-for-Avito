package com.zbistapp.weatherappforavito.location

import android.location.Location

interface ILocationRepo {

    suspend fun currentLocation(callback: (Location) -> Unit)
    fun removeUpdates()
}