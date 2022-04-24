package com.hussien.weatherforecast.presentation.di

import com.hussien.weatherforecast.data.api.WeatherForecastService
import com.hussien.weatherforecast.data.repository.datasource.ForecastRemoteDataSource
import com.hussien.weatherforecast.data.repository.datasource.LocationRemoteDateSource
import com.hussien.weatherforecast.data.repository.datasourceImpl.ForecastRemoteDataSourceImpl
import com.hussien.weatherforecast.data.repository.datasourceImpl.LocationRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideForecastRemoteDataSource(service: WeatherForecastService):ForecastRemoteDataSource{
        return ForecastRemoteDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideLocationRemoteDateSource(service: WeatherForecastService):LocationRemoteDateSource{
        return LocationRemoteDataSourceImpl(service)
    }
}