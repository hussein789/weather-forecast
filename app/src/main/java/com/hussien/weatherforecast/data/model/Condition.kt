package com.hussien.weatherforecast.data.model


import com.google.gson.annotations.SerializedName

data class Condition(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("icon")
    val icon: String? = null,
    @SerializedName("text")
    val text: String? = null
)