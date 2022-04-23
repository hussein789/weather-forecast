package com.hussien.weatherforecast.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hussien.weatherforecast.R
import com.hussien.weatherforecast.data.model.Day
import com.hussien.weatherforecast.domain.usecase.GetWeatherForecastUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val weatherForecastUseCase: GetWeatherForecastUseCase
):ViewModel() {

    private val _weatherUiState = MutableLiveData<WeatherUiState>(WeatherUiState())
    val weatherUiState : LiveData<WeatherUiState> get() = _weatherUiState

    private var currentSelectedDay = SelectedDay.CURRENT

    init {
        getWeatherData()
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            _weatherUiState.value = _weatherUiState.value?.copy(isLoading = true)
            try {
                val weatherData = weatherForecastUseCase.invoke()
                _weatherUiState.value = _weatherUiState.value?.copy(days = weatherData,isLoading = false,null)

            } catch (ex:Exception){
                _weatherUiState.value = _weatherUiState.value?.copy(errorMessage = R.string.generic_error)
            } finally {
                _weatherUiState.value = _weatherUiState.value?.copy(isLoading = false)
            }
        }
    }
}

data class WeatherUiState(
    val days:List<Day?>? = emptyList(),
    val isLoading:Boolean = false,
    val errorMessage:Int? = null
)

enum class SelectedDay {
    CURRENT,NEXT,AFTER
}