package com.example.my_weather_app

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.my_weather_app.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val CITY: String = "hyderabad,in"
    val API: String = "d5f97e334832187c50dc03354326d91d"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getWeatherData()
    }

    private fun getWeatherData() {
        val call: Call<WeatherResponse> =
            RetrofitClient.myApi.getCurrentWeather(CITY, "metric", API)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    weatherResponse?.let {
                        updateUI(it)
                    }
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                showError()
            }
        })

    }
    private fun updateUI(it: WeatherResponse) {
        binding.address.text = "${it.name}, ${it.sys.country}"
        binding.updatedAt.text = "Updated At: ${SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(it.dt * 1000))}"
        binding.status.text = it.weather[0].description.capitalize()
        binding.temperature.text = "${it.main.temp}°C"
        binding.minTemp.text = "Min Temp: ${it.main.temp_min}°C"
        binding.maxTemp.text = "Max Temp: ${it.main.temp_max}°C"
        binding.sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(it.sys.sunrise * 1000))
        binding.sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(it.sys.sunset * 1000))
        binding.wind.text = "${it.wind.speed}"
        binding.pressure.text = "${it.main.pressure}"
        binding.humidity.text = "${it.main.humidity}"

        binding.loader.visibility = View.GONE
        binding.mainPart.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.loader.visibility = View.GONE
        binding.error.visibility = View.VISIBLE
    }
}