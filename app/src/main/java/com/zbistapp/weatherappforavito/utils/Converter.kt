package com.zbistapp.weatherappforavito.utils

import java.text.SimpleDateFormat
import java.util.*

class Converter {

    fun kelvinToCelsius(kelvins: Double): Int =
        (kelvins - 273.15).toInt()

    fun timeToHours(timesTamp: Long): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.US)
        val date = Date(timesTamp * 1000)
        return sdf.format(date)
    }

    fun timeToDate(timesTamp: Long): String {
        val sdf = SimpleDateFormat("EEE',' MMM d", Locale.US)
        val date = Date(timesTamp * 1000)
        return sdf.format(date)
    }
}