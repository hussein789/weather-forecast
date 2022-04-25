package com.hussien.weatherforecast.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.utils.BaseUnitTest
import org.junit.Before
import org.junit.Test

class GetDayNameUseCaseTest : BaseUnitTest(){
    private lateinit var useCase:GetDayNameUseCase

    @Before
    fun setup(){
        useCase = GetDayNameUseCase()
    }

    @Test
    fun invoke_givenCorrectDate_returnCorrectResult(){
        val result = useCase.invoke("2022-04-25")

        assertThat(result).isEqualTo("Monday")
    }

    @Test
    fun invoke_givenNotCorrectDate_returnEmptyText(){
        val result = useCase.invoke("2022dfj-r")

        assertThat(result).isEqualTo("")
    }

    @Test
    fun invoke_givenEmptyText_returnEmptyText(){
        val result = useCase.invoke("")

        assertThat(result).isEqualTo("")
    }
}