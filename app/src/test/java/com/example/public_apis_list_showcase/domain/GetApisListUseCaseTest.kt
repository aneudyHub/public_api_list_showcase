package com.example.public_apis_list_showcase.domain

import com.example.public_apis_list_showcase.data.remote.models.Entry
import com.example.public_apis_list_showcase.data.remote.models.HttpResponseErrorCode
import com.example.public_apis_list_showcase.data.remote.models.RepositoryResponse
import com.example.public_apis_list_showcase.data.repositories.PublicApisSourceRepository
import com.example.public_apis_list_showcase.domain.models.UseCaseErrorType
import com.example.public_apis_list_showcase.domain.models.UseCaseResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetApisListUseCaseTest {

    private lateinit var getApisListUseCase: GetApisListUseCase
    private val publicApisSourceRepository = mock(PublicApisSourceRepository::class.java)

    @Before
    fun setup() {
        getApisListUseCase = GetApisListUseCase(publicApisSourceRepository)
    }

    @Test
    fun `the use case should return a successful response`() = runTest {
        val mockList = mutableListOf<Entry>()
        mockList.add(
            Entry(
                api = "Cat Facts",
                description = "Daily cat facts",
                auth = "",
                https = true,
                cors = "no",
                link = "https://alexwohlbruck.github.io/cat-facts/",
                category = "Animals"
            )
        )
        val mockResponse = RepositoryResponse.Success<List<Entry>>(mockList)
        `when`(publicApisSourceRepository.getEntries()).thenReturn(mockResponse)

        val useCaseResponse = getApisListUseCase("")
        val entriesList = (useCaseResponse as UseCaseResult.Success).data
        Assert.assertEquals(1, entriesList.size)
    }

    @Test
    fun `the use case should return an error, test all possible errors`() = runTest {
        var mockResponse = RepositoryResponse.Error(HttpResponseErrorCode.BAD_REQUEST)
        `when`(publicApisSourceRepository.getEntries()).thenReturn(mockResponse)
        var useCaseResponse = getApisListUseCase("")
        var errorResponse = (useCaseResponse as UseCaseResult.Error).errorType
        Assert.assertEquals(UseCaseErrorType.SERVER_ERROR, errorResponse)


        mockResponse = RepositoryResponse.Error(HttpResponseErrorCode.NOT_FOUND)
        `when`(publicApisSourceRepository.getEntries()).thenReturn(mockResponse)
        useCaseResponse = getApisListUseCase("")
        errorResponse = (useCaseResponse as UseCaseResult.Error).errorType
        Assert.assertEquals(UseCaseErrorType.API_REQUEST_NOT_FOUND, errorResponse)


        mockResponse = RepositoryResponse.Error(HttpResponseErrorCode.INTERNAL_SERVER_ERROR)
        `when`(publicApisSourceRepository.getEntries()).thenReturn(mockResponse)
        useCaseResponse = getApisListUseCase("")
        errorResponse = (useCaseResponse as UseCaseResult.Error).errorType
        Assert.assertEquals(UseCaseErrorType.SERVER_ERROR, errorResponse)


        mockResponse = RepositoryResponse.Error(HttpResponseErrorCode.UNKNOWN)
        `when`(publicApisSourceRepository.getEntries()).thenReturn(mockResponse)
        useCaseResponse = getApisListUseCase("")
        errorResponse = (useCaseResponse as UseCaseResult.Error).errorType
        Assert.assertEquals(UseCaseErrorType.THROWN_EXCEPTION, errorResponse)


        mockResponse = RepositoryResponse.Error(HttpResponseErrorCode.THROWN_EXCEPTION)
        `when`(publicApisSourceRepository.getEntries()).thenReturn(mockResponse)
        useCaseResponse = getApisListUseCase("")
        errorResponse = (useCaseResponse as UseCaseResult.Error).errorType
        Assert.assertEquals(UseCaseErrorType.THROWN_EXCEPTION, errorResponse)


        mockResponse = RepositoryResponse.Error(HttpResponseErrorCode.REQUEST_TIMEOUT)
        `when`(publicApisSourceRepository.getEntries()).thenReturn(mockResponse)
        useCaseResponse = getApisListUseCase("")
        errorResponse = (useCaseResponse as UseCaseResult.Error).errorType
        Assert.assertEquals(UseCaseErrorType.REQUEST_TIMEOUT, errorResponse)
    }


    @Test
    fun `the use case should filter by the value passed in the parameter`() = runTest {
        val mockList = mutableListOf<Entry>()
        mockList.add(
            Entry(
                api = "Cat Facts",
                description = "Daily cat facts",
                auth = "",
                https = true,
                cors = "no",
                link = "https://alexwohlbruck.github.io/cat-facts/",
                category = "Animals"
            )
        )

        `when`(publicApisSourceRepository.getEntries()).thenReturn(
            RepositoryResponse.Success<List<Entry>>(
                mockList
            )
        )
        var useCaseResponse = getApisListUseCase("Dog")
        var entriesList = (useCaseResponse as UseCaseResult.Success).data
        Assert.assertEquals(0, entriesList.size)

        useCaseResponse = getApisListUseCase("Cat")
        entriesList = (useCaseResponse as UseCaseResult.Success).data
        Assert.assertEquals(1, entriesList.size)

        useCaseResponse = getApisListUseCase("Daily")
        entriesList = (useCaseResponse as UseCaseResult.Success).data
        Assert.assertEquals(1, entriesList.size)
    }
}