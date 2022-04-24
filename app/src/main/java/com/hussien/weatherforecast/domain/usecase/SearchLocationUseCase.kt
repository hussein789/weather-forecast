package com.hussien.weatherforecast.domain.usecase

import com.hussien.weatherforecast.domain.repository.LocationRepository
import javax.inject.Inject

class SearchLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(cityName:String) = locationRepository.getSearchedLocations(cityName)
}