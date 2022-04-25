package com.hussien.weatherforecast.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.utils.BaseUnitTest
import org.junit.Before
import org.junit.Test

class GetDateFormattedUseCaseTest : BaseUnitTest() {

    private lateinit var useCase:GetDateFormattedUseCase

    @Before
    fun setup(){
        useCase = GetDateFormattedUseCase()
    }

    @Test
    fun invoke_validDate_returnCorrectResult(){
        val result = useCase.invoke("2022-04-25 16:40")

        assertThat(result).isEqualTo("Monday, 25 Apr 2022")
    }

    @Test
    fun invoke_notValidDate_returnEmptyText(){
        val result = useCase.invoke("254-55-8")

        assertThat(result).isEqualTo("")
    }

    @Test
    fun invoke_emptyDate_returnEmptyText(){
        val result = useCase.invoke("")

        assertThat(result).isEqualTo("")
    }
}