package com.hussien.weatherforecast.presentation.home

import com.google.common.truth.Truth.assertThat
import com.hussien.weatherforecast.R
import com.hussien.weatherforecast.data.model.Location
import com.hussien.weatherforecast.domain.model.WeatherDay
import com.hussien.weatherforecast.domain.model.WeatherModel
import com.hussien.weatherforecast.domain.usecase.GetWeatherForecastUseCase
import com.hussien.weatherforecast.domain.usecase.SearchLocationUseCase
import com.nhaarman.mockitokotlin2.whenever
import com.utils.BaseUnitTest
import com.vezeeta.patients.app.utils.captureValues
import com.vezeeta.patients.app.utils.getValueForTest
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock

class HomeViewModelTest : BaseUnitTest() {

    private lateinit var viewModel: HomeViewModel
    val weatherForecastUseCase = mock(GetWeatherForecastUseCase::class.java)
    val searchLocationUseCase = mock(SearchLocationUseCase::class.java)

    var selectedCity = "San Francisco"

    val weatherModel = WeatherModel("Tuesday, 12 Apr 2022","9:12 AM","San Francisco",
    "main icon url","82.4°F","It’s a sunny day","3 mph","60%",
    listOf(WeatherDay("icon","82.4°/86°F","Today"),
        WeatherDay("icon2","62.6°/71.6°F","Tomorrow"),
        WeatherDay("icon3","61.5°/72°F","Friday")
    ))

    val cairoLocation = Location("Egypt",22.2,"Tuesday, 12 Apr 2022",12,552548.25,"Cairo","Egypt","tz")
    val locations = listOf(cairoLocation)
    val searchedText = "Cai"

    @Test
    fun getWeatherData_searchCity_showProperLoading()= runBlocking{
        whenever(weatherForecastUseCase.invoke(selectedCity)).thenReturn(weatherModel)
        whenever(searchLocationUseCase.invoke(searchedText)).thenReturn(locations)
        mockSuccessCase()

        viewModel.loadingState.captureValues {
            assertThat(values[0]).isEqualTo(false)
            viewModel.getWeatherData(selectedCity)
            assertThat(values[1]).isEqualTo(true)
            assertThat(values.last()).isEqualTo(false)
        }
        viewModel.getWeatherData(selectedCity)
    }

    @Test
    fun getWeatherData_searchCity_emitWeatherDataCorrectly()= runBlocking{
        whenever(weatherForecastUseCase.invoke(selectedCity)).thenReturn(weatherModel)
        whenever(searchLocationUseCase.invoke(searchedText)).thenReturn(locations)
        mockSuccessCase()

        viewModel.getWeatherData(selectedCity)

        val result = viewModel.weatherModelState.getValueForTest()

        assertThat(result).isEqualTo(weatherModel)
    }

    @Test
    fun getWeatherData_apiNotAvailable_showErrorCode()= runBlocking{
        whenever(weatherForecastUseCase.invoke(selectedCity)).thenThrow(IllegalArgumentException("api error"))
        whenever(searchLocationUseCase.invoke(searchedText)).thenReturn(locations)
        mockSuccessCase()

        viewModel.getWeatherData(selectedCity)

        val result = viewModel.errorState.getValueForTest()

        assertThat(result).isEqualTo(R.string.generic_error)
    }

    @Test
    fun onSearchLocationChanged_searchCairo_emitCairoResult() = runBlocking {
        whenever(weatherForecastUseCase.invoke(selectedCity)).thenThrow(IllegalArgumentException("api error"))
        whenever(searchLocationUseCase.invoke(searchedText)).thenReturn(locations)
        mockSuccessCase()

        viewModel.onSearchLocationChanged(searchedText)

        val result = viewModel.searchLocationsState.getValueForTest()

        assertThat(result).isEqualTo(locations)
    }

    @Test
    fun onSearchLocationChanged_networkError_emitGenericError() = runBlocking {
        whenever(weatherForecastUseCase.invoke(selectedCity)).thenThrow(IllegalArgumentException("api error"))
        whenever(searchLocationUseCase.invoke(searchedText)).thenThrow(java.lang.IllegalArgumentException("error happened"))
        mockSuccessCase()

        viewModel.onSearchLocationChanged(searchedText)

        val result = viewModel.errorState.getValueForTest()

        assertThat(result).isEqualTo(R.string.generic_error)
    }

    @Test
    fun onSearchLocationChanged_searchEmptyText_emitNothing() = runBlocking {
        whenever(weatherForecastUseCase.invoke(selectedCity)).thenReturn(weatherModel)
        whenever(searchLocationUseCase.invoke("")).thenReturn(locations)
        mockSuccessCase()

        viewModel.onSearchLocationChanged("")

        val result = viewModel.searchLocationsState.getValueForTest()

        assertThat(result).isEqualTo(null)
    }

    @Test
    fun onLocationClicked_cairoClicked_returnCairoWeather()= runBlocking{
        whenever(weatherForecastUseCase.invoke(cairoLocation.name ?: "")).thenReturn(weatherModel)
        whenever(searchLocationUseCase.invoke(searchedText)).thenReturn(locations)
        mockSuccessCase()

        viewModel.onLocationClicked(cairoLocation)

        val result = viewModel.weatherModelState.getValueForTest()

        assertThat(result).isEqualTo(weatherModel)
        assertThat(viewModel.lastSelectedCity).isEqualTo(cairoLocation.name)
    }

    @Test
    fun onLocationClicked_onRefresh_updateWeatherDate()= runBlocking{
        whenever(weatherForecastUseCase.invoke(selectedCity)).thenReturn(weatherModel)
        whenever(searchLocationUseCase.invoke(searchedText)).thenReturn(locations)
        mockSuccessCase()

        viewModel.onRefresh()

        val result = viewModel.weatherModelState.getValueForTest()

        assertThat(result).isEqualTo(weatherModel)
    }



    private fun mockSuccessCase(){
        viewModel = HomeViewModel(weatherForecastUseCase,searchLocationUseCase)
    }
}