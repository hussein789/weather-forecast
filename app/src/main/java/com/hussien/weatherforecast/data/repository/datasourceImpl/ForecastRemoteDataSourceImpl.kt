package com.hussien.weatherforecast.data.repository.datasourceImpl

import com.hussien.weatherforecast.BuildConfig
import com.hussien.weatherforecast.data.api.WeatherForecastService
import com.hussien.weatherforecast.data.model.WeatherDataList
import com.hussien.weatherforecast.data.repository.datasource.ForecastRemoteDataSource
import javax.inject.Inject

class ForecastRemoteDataSourceImpl @Inject constructor(
    private val weatherForecastService: WeatherForecastService
) : ForecastRemoteDataSource {
    override suspend fun getForecasts(cityName:String): WeatherDataList {
        return weatherForecastService.getWeatherForecast(BuildConfig.API_KEY,cityName)
    }
}