package com.zbistapp.weatherappforavito.ui.main

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zbistapp.weatherappforavito.domain.ILocationRepo
import com.zbistapp.weatherappforavito.domain.INetworkRepo
import kotlinx.coroutines.launch

class MainViewModel(
    private val locationRepo: ILocationRepo,
//    private val networkRepo: INetworkRepo
) : ViewModel() {

    private val _lastLocation = MutableLiveData<Location>()
    val lastLocation: LiveData<Location> = _lastLocation

    fun getLocation() {

        viewModelScope.launch {
            locationRepo.currentLocation() {
                _lastLocation.value = it
                locationRepo.removeUpdates()
            }
        }
    }

    fun getCurrentWeather(location: Location) {

    }
}