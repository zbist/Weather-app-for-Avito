package com.zbistapp.weatherappforavito.domain

import android.location.Location

interface ILocationRepo {

    suspend fun currentLocation(callback: (Location) -> Unit)
    fun removeUpdates()
}