package com.example.my_weather_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {

    private val weather_Data = MutableLiveData<WeatherResponse>()
    val weatherData:LiveData<WeatherResponse> get() = weather_Data

    private val error_messsage = MutableLiveData<Unit>()
    val error: LiveData<Unit> get() = error_messsage

    val API: String = "48b2b4992be7761f5215348cca70e28a"

     fun getWeatherData(city:String) {
        val call: Call<WeatherResponse> =  RetrofitClient.myApi.getCurrentWeather(city, "metric", API)
         call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    weather_Data.value = response.body()
                    } else {
                    error_messsage.value = Unit
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                error_messsage.value = Unit
            }
        })

    }
}