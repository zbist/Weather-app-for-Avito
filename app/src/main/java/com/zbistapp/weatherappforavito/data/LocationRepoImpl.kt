package com.zbistapp.weatherappforavito.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.*
import com.zbistapp.weatherappforavito.domain.ILocationRepo
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

class LocationRepoImpl(context: Context) : ILocationRepo {

    private val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val geocoder = Geocoder(context)
    private lateinit var locationListener: LocationListener
    private var isLocationListenerStarted = false

    companion object {
        private const val REFRESH_RATE = 60_000L
        private const val TRASH_HOLD = 100f
    }

    @SuppressLint("MissingPermission")
    override suspend fun currentLocation(callback: (Location) -> Unit) {
        if (!isLocationListenerStarted) {
            isLocationListenerStarted = !isLocationListenerStarted
            locationListener = LocationListener {
                callback.invoke(it)
            }

            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                REFRESH_RATE,
                TRASH_HOLD,
                locationListener
            )

            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                REFRESH_RATE,
                TRASH_HOLD,
                locationListener
            )
        }
    }

    override suspend fun getLocationFromAddressName(name: String): Address? =
        suspendCoroutine {
            thread {
                val result = geocoder.getFromLocationName(name, 1)

                if (result != null) {
                    it.resumeWith(Result.success(result.firstOrNull()))
                } else {
                    it.resumeWith(Result.failure(Throwable("can't find this address")))
                }
            }
        }

    override fun removeUpdates() {
        isLocationListenerStarted = !isLocationListenerStarted
        locationManager.removeUpdates(locationListener)
    }
}