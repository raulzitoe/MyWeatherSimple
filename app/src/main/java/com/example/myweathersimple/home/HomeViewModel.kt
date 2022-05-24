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
    var weatherIcon = MutableLiveData<Int>()

    fun requestWeatherData() {
        uiScope.launch {
            val service = ServiceBuilder.getInstance().create(ApiInterface::class.java)
            val apiKey = BuildConfig.apiKey
            val result = service.getWeather("43.65", "-79.38","metric", apiKey)
            // Checking the results
            Log.d("test: ", result.body().toString())
            weatherData.postValue(result.body()!!)
            weatherData.value?.weather?.get(0)?.let { determineWeatherIcon(it.icon) }
        }
    }

    fun determineWeatherIcon(iconID: String){
        weatherIcon.postValue(when(iconID){
            "01d" -> R.drawable.ic_01d
            "02d" -> R.drawable.ic_02d
            "03d" -> R.drawable.ic_03d
            "04d" -> R.drawable.ic_04d
            "09d" -> R.drawable.ic_09d
            "10d" -> R.drawable.ic_10d
            "11d" -> R.drawable.ic_11d
            "13d" -> R.drawable.ic_13d
            "50d" -> R.drawable.ic_50d
            else -> R.drawable.ic_01d
        })
    }
}
