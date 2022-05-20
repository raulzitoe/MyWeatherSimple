package com.example.myweathersimple.home


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweathersimple.ApiInterface
import com.example.myweathersimple.BuildConfig
import com.example.myweathersimple.ServiceBuilder
import com.example.myweathersimple.WeatherModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val uiScope = CoroutineScope(Dispatchers.IO)
    val weather = MutableLiveData<WeatherModel>()

    fun getWeatherData() {
        uiScope.launch {
            val service = ServiceBuilder.getInstance().create(ApiInterface::class.java)
            val apiKey = BuildConfig.apiKey
            val result = service.getWeather("43.65", "-79.38","metric", apiKey)
            // Checking the results
            Log.d("test: ", result.body().toString())
            weather.postValue(result.body()!!)
        }
    }
}
