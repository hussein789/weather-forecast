package com.hussien.weatherforecast.domain.repository

import com.hussien.weatherforecast.data.model.Day

interface WeatherForecastRepository {
    suspend fun getWeatherForecast():List<Day?>?
}