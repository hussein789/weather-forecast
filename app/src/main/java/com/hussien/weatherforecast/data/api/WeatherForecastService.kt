package com.hussien.weatherforecast.data.api

import com.hussien.weatherforecast.data.model.Location
import com.hussien.weatherforecast.data.model.WeatherDataList
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastService {

    @GET("forecast.json")
    suspend fun getWeatherForecast(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("days") days: Int = 3,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): WeatherDataList

    @GET("search.json")
    suspend fun searchLocation(
        @Query("key") apiKey: String,
        @Query("q") query: String,
    ): List<Location>

}