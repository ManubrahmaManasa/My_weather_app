package com.example.my_weather_app

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    val CITY: String = "hyderabad,in"
    val API: String = "d5f97e334832187c50dc03354326d91d"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        findViewById<TextView>(R.id.address).text = "${it.name}, ${it.sys.country}"
        findViewById<TextView>(R.id.updated_at).text = "Updated At: ${SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(it.dt * 1000))}"
        findViewById<TextView>(R.id.status).text = it.weather[0].description.capitalize()
        findViewById<TextView>(R.id.temperature).text = "${it.main.temp}°C"
        findViewById<TextView>(R.id.min_temp).text = "Min Temp: ${it.main.temp_min}°C"
        findViewById<TextView>(R.id.max_temp).text = "Max Temp: ${it.main.temp_max}°C"
        findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(it.sys.sunrise * 1000))
        findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(it.sys.sunset * 1000))
        findViewById<TextView>(R.id.wind).text = "${it.wind.speed}"
        findViewById<TextView>(R.id.pressure).text = "${it.main.pressure}"
        findViewById<TextView>(R.id.humidity).text = "${it.main.humidity}"

        findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
        findViewById<RelativeLayout>(R.id.mainPart).visibility = View.VISIBLE
    }

    private fun showError() {
        findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
        findViewById<TextView>(R.id.error).visibility = View.VISIBLE
    }


}