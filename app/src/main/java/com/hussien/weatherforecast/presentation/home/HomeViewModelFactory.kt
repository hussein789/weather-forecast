package com.hussien.weatherforecast.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hussien.weatherforecast.domain.usecase.GetWeatherForecastUseCase
import com.hussien.weatherforecast.domain.usecase.SearchLocationUseCase
import java.lang.IllegalArgumentException
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
    private val searchLocationUseCase: SearchLocationUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(getWeatherForecastUseCase,searchLocationUseCase) as T
        throw IllegalArgumentException("unknown view model found")
    }
}