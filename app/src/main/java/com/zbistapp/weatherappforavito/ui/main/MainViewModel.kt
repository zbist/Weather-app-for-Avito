package com.zbistapp.weatherappforavito.ui.main

import android.location.Location
import androidx.lifecycle.*
import com.zbistapp.weatherappforavito.domain.ILocationRepo
import com.zbistapp.weatherappforavito.domain.INetworkRepo
import com.zbistapp.weatherappforavito.domain.responses.current.CurrentWeatherResponse
import com.zbistapp.weatherappforavito.domain.responses.details.DetailedWeatherResponse
import kotlinx.coroutines.launch

class MainViewModel(
    private val locationRepo: ILocationRepo,
    private val networkRepo: INetworkRepo
) : ViewModel() {

    private val _locationLiveData = MutableLiveData<Location>()
    val locationLiveData: LiveData<Location> = _locationLiveData
    private val _currentWeatherLiveData = MutableLiveData<CurrentWeatherResponse>()
    val currentWeatherLiveData: LiveData<CurrentWeatherResponse> = _currentWeatherLiveData
    private val _errorLiveData = MutableLiveData<Throwable>()
    val errorLiveData: LiveData<Throwable> = _errorLiveData
    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData
    private val _detailedWeatherLiveData = MutableLiveData<DetailedWeatherResponse>()
    val detailedWeatherLiveData: LiveData<DetailedWeatherResponse> = _detailedWeatherLiveData

    fun getLocation() {
        _progressBarLiveData.value = true
        viewModelScope.launch {
            locationRepo.currentLocation {
                _locationLiveData.value = it
                locationRepo.removeUpdates()
            }
        }
    }

    fun getCurrentWeather(location: Location) {
        viewModelScope.launch {
            networkRepo.fetchCurrentWeather(location) {
                if (it.isSuccess) {
                    _currentWeatherLiveData.value = it.getOrNull()
                } else {
                    _errorLiveData.value = it.exceptionOrNull()
                }
            }
        }
        _progressBarLiveData.value = false
    }

    fun getDetailedWeather(location: Location) {
        viewModelScope.launch {
            networkRepo.fetchDetailedWeather(location) {
                if (it.isSuccess) {
                    _detailedWeatherLiveData.value = it.getOrNull()
                } else {
                    _errorLiveData.value = it.exceptionOrNull()
                }
            }
        }
    }

    fun getLocationFromAddressName(name: String) {
        if (name.isNotBlank()) {
            _progressBarLiveData.value = true
            viewModelScope.launch {
                val address = locationRepo.getLocationFromAddressName(name)
                if (address != null) {
                    val location = Location("")
                    location.latitude = address.latitude
                    location.longitude = address.longitude
                    _locationLiveData.value = location
                } else {
                    _errorLiveData.value = Throwable("can't find this address")
                }
            }
        }
    }
}


class MainViewModelFactory(
    private val locationRepo: ILocationRepo,
    private val networkRepo: INetworkRepo
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(locationRepo, networkRepo) as T
}