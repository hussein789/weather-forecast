package com.hussien.weatherforecast.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.hussien.weatherforecast.data.model.*
import com.hussien.weatherforecast.domain.repository.WeatherForecastRepository
import com.nhaarman.mockitokotlin2.whenever
import com.utils.BaseUnitTest
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock

class GetWeatherForecastUseCaseTest : BaseUnitTest() {
    private lateinit var useCase: GetWeatherForecastUseCase

    val getDateFormattedUseCase = mock(GetDateFormattedUseCase::class.java)
    val getTimeFormattedUseCase = mock(GetTimeFormattedUseCase::class.java)
    val getDayNameUseCharArray = mock(GetDayNameUseCase::class.java)
    val weatherRepo = mock(WeatherForecastRepository::class.java)

    val searchedText = "Cairo"

    val remoteModel = WeatherDataList(
        current = Current(
            condition = Condition(icon = "main icon url", text = "Sunny"),
            tempF = 82.5,
            windMph = 65.5,
            humidity = 80
        ),
        forecast = Forecast(
            listOf(
                Forecastday(
                    date = "2022-04-25",
                    day = Day(
                        condition = ConditionX(icon = "first day icon"),
                        mintempF = 40.5,
                        maxtempF = 60.5
                    )
                ),
                Forecastday(
                    date = "2022-04-26",
                    day = Day(
                        condition = ConditionX(icon = "second day icon"),
                        mintempF = 80.5,
                        maxtempF = 100.5
                    )
                ),
                Forecastday(
                    date = "2022-04-27",
                    day = Day(
                        condition = ConditionX(icon = "third day icon"),
                        mintempF = 120.5,
                        maxtempF = 220.5
                    )
                )
            )
        ),
        location = Location(localtime = "2022-04-25 16:38", name = "Cairo")
    )

    @Test
    fun invoke_givenModel_MapToDomainModelCorrectly() = runBlocking {
        mockSuccessCase()

        val result = useCase.invoke(searchedText)

        assertThat(result.cityName).isEqualTo("Cairo")
        assertThat(result.description).isEqualTo("It's a Sunny day")
        assertThat(result.humidity).isEqualTo("80%")
        assertThat(result.localDate).isEqualTo("Monday, 25 Apr 2022")
        assertThat(result.localTime).isEqualTo("4:38 PM")
        assertThat(result.mainDegree).isEqualTo("82.5°F")
        assertThat(result.wind).isEqualTo("65.5 mph")
        assertThat(result.weatherDays[0].dayName).isEqualTo("Monday")
        assertThat(result.weatherDays[0].icon).isEqualTo("first day icon")
        assertThat(result.weatherDays[0].minMaxDegree).isEqualTo("40.5°/60.5°F")
        assertThat(result.weatherDays[1].dayName).isEqualTo("Tuesday")
        assertThat(result.weatherDays[1].icon).isEqualTo("second day icon")
        assertThat(result.weatherDays[1].minMaxDegree).isEqualTo("80.5°/100.5°F")
        assertThat(result.weatherDays[2].dayName).isEqualTo("Wednesday")
        assertThat(result.weatherDays[2].icon).isEqualTo("third day icon")
        assertThat(result.weatherDays[2].minMaxDegree).isEqualTo("120.5°/220.5°F")


    }


    private fun mockSuccessCase() = runBlocking {
        whenever(getDateFormattedUseCase.invoke("2022-04-25 16:38")).thenReturn("Monday, 25 Apr 2022")
        whenever(getTimeFormattedUseCase.invoke("2022-04-25 16:38")).thenReturn("4:38 PM")
        whenever(getDayNameUseCharArray.invoke("2022-04-25")).thenReturn("Monday")
        whenever(getDayNameUseCharArray.invoke("2022-04-26")).thenReturn("Tuesday")
        whenever(getDayNameUseCharArray.invoke("2022-04-27")).thenReturn("Wednesday")
        whenever(weatherRepo.getWeatherForecast(searchedText)).thenReturn(remoteModel)
        useCase = GetWeatherForecastUseCase(
            weatherRepo,
            getDateFormattedUseCase,
            getTimeFormattedUseCase,
            getDayNameUseCharArray
        )
    }


}