package com.hussien.weatherforecast.data.model


import com.google.gson.annotations.SerializedName

data class WeatherDataList(
    @SerializedName("current")
    val current: Current? = null,
    @SerializedName("forecast")
    val forecast: Forecast? = null,
    @SerializedName("location")
    val location: Location? = null
)