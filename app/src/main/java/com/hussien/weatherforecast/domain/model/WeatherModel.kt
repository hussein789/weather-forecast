package com.hussien.weatherforecast.domain.model


data class WeatherModel(
    val localDate:String,
    val localTime: String,
    val cityName:String,
    val mainIcon:String,
    val mainDegree:String,
    val description:String,
    val wind:String,
    val humidity:String,
    val weatherDays:List<WeatherDay>
)

data class WeatherDay(
    val icon:String,
    val minMaxDegree:String,
    val dayName:String
)
