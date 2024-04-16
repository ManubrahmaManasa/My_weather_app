package com.example.my_weather_app

import android.text.Editable

data class WeatherResponse(
    val main:Main,
    val sys: Sys,
    val wind: Wind,
    val weather: List<Weather>,
    val dt: Long,
    val cityname: Editable,
)
