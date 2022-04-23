package com.hussien.weatherforecast.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hussien.weatherforecast.domain.usecase.GetWeatherForecastUseCase
import java.lang.IllegalArgumentException
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(getWeatherForecastUseCase) as T
        throw IllegalArgumentException("unknown view model found")
    }
}