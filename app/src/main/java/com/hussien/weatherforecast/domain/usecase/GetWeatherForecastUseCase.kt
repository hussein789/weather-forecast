package com.hussien.weatherforecast.domain.usecase

import com.hussien.weatherforecast.domain.repository.WeatherForecastRepository
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository
) {
    suspend operator fun invoke() = weatherForecastRepository.getWeatherForecast()
}