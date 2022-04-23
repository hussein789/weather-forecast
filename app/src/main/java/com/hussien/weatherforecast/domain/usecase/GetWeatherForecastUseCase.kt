package com.hussien.weatherforecast.domain.usecase

import com.hussien.weatherforecast.data.model.WeatherDataList
import com.hussien.weatherforecast.domain.model.WeatherDay
import com.hussien.weatherforecast.domain.model.WeatherModel
import com.hussien.weatherforecast.domain.repository.WeatherForecastRepository
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository,
    private val getDateFormattedUseCase: GetDateFormattedUseCase,
    private val getTimeFormattedUseCase: GetTimeFormattedUseCase,
    private val getDayNameUseCase: GetDayNameUseCase
) {
    suspend operator fun invoke(): WeatherModel {
        val response = weatherForecastRepository.getWeatherForecast()
        val weatherModel = getMappedModel(response)
        return weatherModel
    }

    private fun getMappedModel(response: WeatherDataList): WeatherModel {
        val weatherModel = WeatherModel(
            getDateFormattedUseCase.invoke(response.location?.localtime ?: ""),
            getTimeFormattedUseCase.invoke(response.location?.localtime ?: ""),
            response.location?.name ?: "",
            response.current?.condition?.icon ?: "",
            "${response.current?.tempF}°F",
            "It's a ${response.current?.condition?.text} day",
            "${response.current?.windMph} mph",
            "${response.current?.humidity}%",
            listOf(
                WeatherDay(
                    response?.forecast?.forecastday?.get(0)?.day?.condition?.icon ?: "",
                    "${response?.forecast?.forecastday?.get(0)?.day?.mintempF}°/${
                        response?.forecast?.forecastday?.get(
                            0
                        )?.day?.maxtempF
                    }°F",
                    getDayNameUseCase.invoke(response.forecast?.forecastday?.get(0)?.date ?: "")
                ),
                WeatherDay(
                    response?.forecast?.forecastday?.get(1)?.day?.condition?.icon ?: "",
                    "${response?.forecast?.forecastday?.get(1)?.day?.mintempF}°/${
                        response?.forecast?.forecastday?.get(
                            1
                        )?.day?.maxtempF
                    }°F",
                    getDayNameUseCase.invoke(response.forecast?.forecastday?.get(1)?.date ?: "")
                ),
                WeatherDay(
                    response?.forecast?.forecastday?.get(2)?.day?.condition?.icon ?: "",
                    "${response?.forecast?.forecastday?.get(2)?.day?.mintempF}°/${
                        response?.forecast?.forecastday?.get(
                            2
                        )?.day?.maxtempF
                    }°F",
                    getDayNameUseCase.invoke(response.forecast?.forecastday?.get(2)?.date ?: "")
                )

            )
        )
        return weatherModel
    }
}