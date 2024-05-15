package com.example.my_weather_app

data class WeatherResponse(
    val main:Main,
    val sys: Sys,
    val wind: Wind,
    val weather: List<Weather>,
    val dt: Long,
    val name: String,
)
