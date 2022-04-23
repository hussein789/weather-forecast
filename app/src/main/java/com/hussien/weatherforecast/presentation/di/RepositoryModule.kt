package com.hussien.weatherforecast.presentation.di

import com.hussien.weatherforecast.data.repository.WeatherForecastRepositoryImpl
import com.hussien.weatherforecast.data.repository.datasource.ForecastRemoteDataSource
import com.hussien.weatherforecast.domain.repository.WeatherForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideWeatherForecastRepository(remoteDataSource: ForecastRemoteDataSource):WeatherForecastRepository{
        return WeatherForecastRepositoryImpl(remoteDataSource)
    }
}