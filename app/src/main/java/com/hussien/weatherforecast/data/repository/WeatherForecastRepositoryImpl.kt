package com.hussien.weatherforecast.data.repository

import com.hussien.weatherforecast.data.model.Day
import com.hussien.weatherforecast.data.repository.datasource.ForecastRemoteDataSource
import com.hussien.weatherforecast.domain.repository.WeatherForecastRepository

class WeatherForecastRepositoryImpl(
    private val forecastRemoteDataSource: ForecastRemoteDataSource
) : WeatherForecastRepository {
    override suspend fun getWeatherForecast(): List<Day?>? {
        return forecastRemoteDataSource.getForecasts().forecast?.forecastday?.take(3)?.map { it.day }
    }
}