package com.hussien.weatherforecast.data.repository.datasource

import com.hussien.weatherforecast.data.model.WeatherDataList

interface ForecastRemoteDataSource {
    suspend fun getForecasts(cityName:String):WeatherDataList
}