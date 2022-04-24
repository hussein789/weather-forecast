package com.hussien.weatherforecast.domain.repository

import com.hussien.weatherforecast.data.model.Location

interface LocationRepository {
    suspend fun getSearchedLocations(cityName:String):List<Location>
}