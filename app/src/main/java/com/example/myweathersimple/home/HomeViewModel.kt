package com.example.myweathersimple.home


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweathersimple.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val uiScope = CoroutineScope(Dispatchers.IO)
    val weatherData = MutableLiveData<WeatherModel>()
    val forecastData = MutableLiveData<ForecastModel>()
    var weatherIcon = MutableLiveData<Int>()
    var coordinates = MutableLiveData<Coordinates>()
    var locationData = MutableLiveData<List<AutocompleteModel>>()
    val service = ServiceBuilder.getInstance().create(ApiInterface::class.java)
    val apiKey = BuildConfig.apiKey

    fun requestWeatherAndForecastData() {
        uiScope.launch {
            val result = service.requestWeather(
                coordinates.value?.latitude.toString(),
                coordinates.value?.longitude.toString(),
                "metric",
                apiKey
            )
            val forecast = service.requestForecast(
                coordinates.value?.latitude.toString(),
                coordinates.value?.latitude.toString(),
                "metric",
                "24",
                apiKey
            )
            // Checking the results
            Log.d("test: ", result.body().toString())
            weatherData.postValue(result.body())
            forecastData.postValue(forecast.body())
            weatherData.value?.weather?.get(0)?.let { determineWeatherIcon(it.icon) }
        }
    }

    fun requestLocationData(query: String) {
        uiScope.launch {
            val result = service.requestLocation(query, "5", apiKey)
            locationData.postValue(result.body())
        }
    }

    private fun determineWeatherIcon(iconID: String) {
        weatherIcon.postValue(
            when (iconID) {
                "01d" -> R.drawable.ic_01d
                "02d" -> R.drawable.ic_02d
                "03d" -> R.drawable.ic_03d
                "04d" -> R.drawable.ic_04d
                "09d" -> R.drawable.ic_09d
                "10d" -> R.drawable.ic_10d
                "11d" -> R.drawable.ic_11d
                "13d" -> R.drawable.ic_13d
                "50d" -> R.drawable.ic_50d
                "01n" -> R.drawable.ic_01n
                "02n" -> R.drawable.ic_02n
                "03n" -> R.drawable.ic_03n
                "04n" -> R.drawable.ic_04n
                "09n" -> R.drawable.ic_09n
                "10n" -> R.drawable.ic_10n
                "11n" -> R.drawable.ic_11n
                "13n" -> R.drawable.ic_13n
                "50n" -> R.drawable.ic_50n
                else -> R.drawable.ic_01d
            }
        )
    }
}
