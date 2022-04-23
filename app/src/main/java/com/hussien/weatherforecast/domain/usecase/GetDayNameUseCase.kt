package com.hussien.weatherforecast.domain.usecase

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetDayNameUseCase @Inject constructor() {
    operator fun invoke(dateString:String):String{
        return try {
            val inFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val date = inFormat.parse(dateString)
            val outFormat = SimpleDateFormat("EEEE", Locale.ENGLISH)
            val output = outFormat.format(date)
            output
        } catch (ex:Exception){
            ""
        }
    }
}