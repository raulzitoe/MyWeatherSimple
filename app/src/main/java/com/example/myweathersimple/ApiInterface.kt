package com.example.myweathersimple

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") units: String,
        @Query("appid") key: String
    ): Response<WeatherModel>

}