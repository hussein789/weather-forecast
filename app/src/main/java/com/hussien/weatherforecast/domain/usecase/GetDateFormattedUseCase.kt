package com.hussien.weatherforecast.domain.usecase

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetDateFormattedUseCase @Inject constructor() {

    operator fun invoke(date:String):String{
        return try {
            var dateFormatter = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH)
            val newDate = dateFormatter.parse(date)
            dateFormatter = SimpleDateFormat("EEEEE, dd MMM yyyy",Locale.ENGLISH)
            dateFormatter.format(newDate)
        } catch (ex:Exception){
            ""
        }

    }
}