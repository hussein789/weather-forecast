package com.hussien.weatherforecast.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hussien.weatherforecast.R
import com.hussien.weatherforecast.data.model.Day
import com.hussien.weatherforecast.data.model.Location
import com.hussien.weatherforecast.domain.model.WeatherModel
import com.hussien.weatherforecast.domain.usecase.GetWeatherForecastUseCase
import com.hussien.weatherforecast.domain.usecase.SearchLocationUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val weatherForecastUseCase: GetWeatherForecastUseCase,
    private val searchLocationUseCase: SearchLocationUseCase
):ViewModel() {

    private val _weatherModelState = MutableLiveData<WeatherModel>()
    val weatherModelState : LiveData<WeatherModel> get() = _weatherModelState

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _errorState = MutableLiveData<Int?>()
    val errorState: LiveData<Int?> get() = _errorState

    private val _searchLocationsState = MutableLiveData<List<Location>>()
    val searchLocationsState: LiveData<List<Location>> get() = _searchLocationsState


    private var lastSelectedCity = "San Francisco"

    init {
        getWeatherData(lastSelectedCity)
    }

    private fun getWeatherData(cityName:String) {
        viewModelScope.launch {
            _loadingState.value = true
            try {
                val weatherData = weatherForecastUseCase.invoke(cityName)
                _weatherModelState.value = weatherData

            } catch (ex:Exception){
                _errorState.value = R.string.generic_error
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun onSearchLocationChanged(searchCity: String) {
        viewModelScope.launch {
            delay(1000)
            try {
                val locations = searchLocationUseCase.invoke(searchCity)
                _searchLocationsState.value = locations
            } catch (ex:Exception){
                _errorState.value = R.string.generic_error
            }
        }
    }

    fun onLocationClicked(location: Location) {
        location.name?.let {
            lastSelectedCity = location.name
            getWeatherData(location.name)
        }
    }

    fun onRefresh() {
        getWeatherData(lastSelectedCity)
    }
}

data class WeatherUiState(
    val weatherModel:WeatherModel? = null,
    val searchLocations:MutableList<Location> = mutableListOf(),
    val isLoading:Boolean = false,
    val errorMessage:Int? = null
)
