package com.hussien.weatherforecast.domain.repository

import com.hussien.weatherforecast.data.model.WeatherDataList

interface WeatherForecastRepository {
    suspend fun getWeatherForecast(): WeatherDataList
}