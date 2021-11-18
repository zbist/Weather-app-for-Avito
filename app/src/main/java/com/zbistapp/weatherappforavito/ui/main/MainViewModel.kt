package com.zbistapp.weatherappforavito.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zbistapp.weatherappforavito.location.ILocationRepo
import kotlinx.coroutines.launch

class MainViewModel(private val locationRepo: ILocationRepo): ViewModel() {

    private val _lastLocation = MutableLiveData<String>()
    val lastLocation: LiveData<String> = _lastLocation

    fun getLocation() {

        viewModelScope.launch {
            locationRepo.currentLocation() {
                _lastLocation.value = "${it.latitude} ${it.longitude}"
            }
        }
    }
}