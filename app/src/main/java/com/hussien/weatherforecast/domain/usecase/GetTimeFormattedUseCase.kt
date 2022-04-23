package com.hussien.weatherforecast.domain.usecase

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetTimeFormattedUseCase @Inject constructor() {
    operator fun invoke(date:String):String{
        return try {
            var dateFormatter = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH)
            val newDate = dateFormatter.parse(date)
            dateFormatter = SimpleDateFormat("hh:mm a",Locale.ENGLISH)
            dateFormatter.format(newDate)
        } catch (ex:Exception){
            ""
        }

    }
}