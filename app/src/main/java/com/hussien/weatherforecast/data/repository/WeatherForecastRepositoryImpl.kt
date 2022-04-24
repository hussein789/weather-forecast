package com.hussien.weatherforecast.data.repository

import com.hussien.weatherforecast.data.model.Day
import com.hussien.weatherforecast.data.model.WeatherDataList
import com.hussien.weatherforecast.data.repository.datasource.ForecastRemoteDataSource
import com.hussien.weatherforecast.domain.repository.WeatherForecastRepository

class WeatherForecastRepositoryImpl(
    private val forecastRemoteDataSource: ForecastRemoteDataSource
) : WeatherForecastRepository {
    override suspend fun getWeatherForecast(cityName:String): WeatherDataList {
        return forecastRemoteDataSource.getForecasts(cityName)
    }
}