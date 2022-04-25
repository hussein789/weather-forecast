package com.hussien.weatherforecast.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.hussien.weatherforecast.data.model.Location
import com.hussien.weatherforecast.domain.repository.LocationRepository
import com.nhaarman.mockitokotlin2.whenever
import com.utils.BaseUnitTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class SearchLocationUseCaseTest : BaseUnitTest(){
    private lateinit var useCase:SearchLocationUseCase
    val locationRepository = mock(LocationRepository::class.java)

    val cairoLocation = Location("Egypt",22.2,"Tuesday, 12 Apr 2022",12,552548.25,"Cairo","Egypt","tz")
    val locations = listOf(cairoLocation)
    val searchedText = "Cai"

    @Before
    fun setup()= runBlocking{
        whenever(locationRepository.getSearchedLocations(searchedText)).thenReturn(locations)
        useCase = SearchLocationUseCase(locationRepository)
    }

    @Test
    fun invokeUseCase_returnRepoResponse()= runBlocking{
        val result = useCase.invoke(searchedText)

        assertThat(result).isEqualTo(locations)
    }
}