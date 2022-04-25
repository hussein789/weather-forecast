package com.hussien.weatherforecast.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hussien.weatherforecast.R
import com.hussien.weatherforecast.domain.model.WeatherModel
import com.hussien.weatherforecast.domain.usecase.GetWeatherForecastUseCase
import com.hussien.weatherforecast.presentation.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val weatherForecastUseCase: GetWeatherForecastUseCase
) : ViewModel() {

    private val _weatherModelState = SingleLiveEvent<WeatherModel>()
    val weatherModelState : LiveData<WeatherModel> get() = _weatherModelState

    private val _errorState = SingleLiveEvent<Int?>()
    val errorState: LiveData<Int?> get() = _errorState

    var lastSelectedCity = "San Francisco"

    init {
        getWeatherData(lastSelectedCity)
    }

    fun getWeatherData(cityName:String) {
        viewModelScope.launch {
            try {
                val weatherData = weatherForecastUseCase.invoke(cityName)
                _weatherModelState.value = weatherData

            } catch (ex:Exception){
                _errorState.value = R.string.generic_error
            }
        }
    }

}