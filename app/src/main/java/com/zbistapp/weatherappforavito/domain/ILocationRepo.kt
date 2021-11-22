package com.zbistapp.weatherappforavito.domain

import android.location.Address
import android.location.Location

interface ILocationRepo {

    suspend fun currentLocation(callback: (Location) -> Unit)
    suspend fun getLocationFromAddressName(name: String): Address?
    fun removeUpdates()
}