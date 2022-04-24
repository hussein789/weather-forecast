package com.hussien.weatherforecast.data.repository.datasource

import com.hussien.weatherforecast.data.model.Location


interface LocationRemoteDateSource {
    suspend fun searchLocations(cityName:String):List<Location>
}