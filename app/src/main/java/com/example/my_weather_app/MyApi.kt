package com.example.my_weather_app;

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {
    @GET("weather")
    fun getCurrentWeather(
        @Query("q") city : String,
        @Query("units") units:String,
        @Query("appid") apiKey : String
    ): Call<WeatherResponse>

}
