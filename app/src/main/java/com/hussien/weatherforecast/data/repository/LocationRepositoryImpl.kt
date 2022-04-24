package com.hussien.weatherforecast.data.repository

import com.hussien.weatherforecast.data.model.Location
import com.hussien.weatherforecast.data.repository.datasource.LocationRemoteDateSource
import com.hussien.weatherforecast.domain.repository.LocationRepository

class LocationRepositoryImpl(
    private val locationRemoteDateSource: LocationRemoteDateSource
) : LocationRepository {

    override suspend fun getSearchedLocations(cityName: String):List<Location> {
        return locationRemoteDateSource.searchLocations(cityName)
    }
}