package com.hussien.weatherforecast.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.utils.BaseUnitTest
import org.junit.Before
import org.junit.Test

class GetTimeFormattedUseCaseTest : BaseUnitTest(){

    private lateinit var useCase:GetTimeFormattedUseCase

    @Before
    fun setup(){
        useCase = GetTimeFormattedUseCase()
    }

    @Test
    fun invoke_validDate_returnCorrectResult(){
        val result = useCase.invoke("2022-04-25 16:40")

        assertThat(result).isEqualTo("04:40 PM")
    }

    @Test
    fun invoke_notValidDate_returnEmptyText(){
        val result = useCase.invoke("202288-dfd")

        assertThat(result).isEqualTo("")
    }

    @Test
    fun invoke_emptyText_returnEmptyText(){
        val result = useCase.invoke("")

        assertThat(result).isEqualTo("")
    }
}