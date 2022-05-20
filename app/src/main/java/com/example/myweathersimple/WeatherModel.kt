package com.example.myweathersimple

import androidx.lifecycle.MutableLiveData


data class WeatherModel(
    val main: Main
)

data class Main(
    var temp: Float,
    var feels_like: Float,
    var temp_min: Float,
    var temp_max: Float,
    var pressure: Int,
    var humidity: Int
)