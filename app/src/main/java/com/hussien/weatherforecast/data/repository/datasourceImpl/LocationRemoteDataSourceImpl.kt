package com.hussien.weatherforecast.data.repository.datasourceImpl

import com.hussien.weatherforecast.BuildConfig
import com.hussien.weatherforecast.data.api.WeatherForecastService
import com.hussien.weatherforecast.data.model.Location
import com.hussien.weatherforecast.data.repository.datasource.LocationRemoteDateSource

class LocationRemoteDataSourceImpl(
    private val service: WeatherForecastService
) : LocationRemoteDateSource {
    override suspend fun searchLocations(cityName: String): List<Location> {
        return service.searchLocation(BuildConfig.API_KEY,cityName)
    }
}