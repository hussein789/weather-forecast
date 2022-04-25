package com.hussien.weatherforecast.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hussien.weatherforecast.domain.usecase.GetWeatherForecastUseCase
import java.lang.IllegalArgumentException
import javax.inject.Inject

class SplashViewModelFactory @Inject constructor(
    private val weatherForecastUseCase: GetWeatherForecastUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SplashViewModel::class.java))
            return SplashViewModel(weatherForecastUseCase) as T
        throw IllegalArgumentException("unknown view model found")
    }
}