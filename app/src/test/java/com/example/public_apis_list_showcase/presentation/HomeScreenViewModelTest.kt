package com.example.public_apis_list_showcase.presentation

import com.example.public_apis_list_showcase.R
import com.example.public_apis_list_showcase.data.remote.models.Entry
import com.example.public_apis_list_showcase.data.remote.models.HttpResponseErrorCode
import com.example.public_apis_list_showcase.data.remote.models.RepositoryResponse
import com.example.public_apis_list_showcase.data.repositories.PublicApisSourceRepository
import com.example.public_apis_list_showcase.domain.GetApisListUseCase
import com.example.public_apis_list_showcase.ui.navigation.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class HomeScreenViewModelTest {

    private lateinit var viewModel: HomeScreenViewModel
    private val publicApisSourceRepositoryMock = mock(PublicApisSourceRepository::class.java)
    private val getApisListUseCase = GetApisListUseCase(publicApisSourceRepositoryMock)
    private val navigator = Navigator()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeScreenViewModel(getApisListUseCase, navigator)
    }


    @Test
    fun `apis list request should fail`() = testScope.runBlockingTest {
        var repositoryError: RepositoryResponse.Error
        var uiState: HomeScreenViewModel.UiState

        /*
        * when the use case fails due to BAD_REQUEST error
        * */

        repositoryError = RepositoryResponse.Error(HttpResponseErrorCode.BAD_REQUEST)
        `when`(publicApisSourceRepositoryMock.getEntries()).thenReturn(repositoryError)

        viewModel.getApisList()
        uiState = viewModel.uiState.first()

        Assert.assertEquals(R.string.network_server_error, uiState.errorResourceId)
        Assert.assertEquals(false, uiState.isLoading)


        /*
        * when the use case fails due to NOT_FOUND error
        * */


        repositoryError = RepositoryResponse.Error(HttpResponseErrorCode.NOT_FOUND)
        `when`(publicApisSourceRepositoryMock.getEntries()).thenReturn(repositoryError)

        viewModel.getApisList()
        uiState = viewModel.uiState.first()

        Assert.assertEquals(R.string.network_unexpected_error, uiState.errorResourceId)
        Assert.assertEquals(false, uiState.isLoading)


        /*
        * when the use case fails due to INTERNAL_SERVER_ERROR error
        * */


        repositoryError = RepositoryResponse.Error(HttpResponseErrorCode.INTERNAL_SERVER_ERROR)
        `when`(publicApisSourceRepositoryMock.getEntries()).thenReturn(repositoryError)

        viewModel.getApisList()
        uiState = viewModel.uiState.first()

        Assert.assertEquals(R.string.network_server_error, uiState.errorResourceId)
        Assert.assertEquals(false, uiState.isLoading)


        /*
        * when the use case fails due to UNKNOWN error
        * */


        repositoryError = RepositoryResponse.Error(HttpResponseErrorCode.UNKNOWN)
        `when`(publicApisSourceRepositoryMock.getEntries()).thenReturn(repositoryError)

        viewModel.getApisList()
        uiState = viewModel.uiState.first()

        Assert.assertEquals(R.string.network_unexpected_error, uiState.errorResourceId)
        Assert.assertEquals(false, uiState.isLoading)


        /*
        * when the use case fails due to THROWN_EXCEPTION error
        * */


        repositoryError = RepositoryResponse.Error(HttpResponseErrorCode.THROWN_EXCEPTION)
        `when`(publicApisSourceRepositoryMock.getEntries()).thenReturn(repositoryError)

        viewModel.getApisList()
        uiState = viewModel.uiState.first()

        Assert.assertEquals(R.string.network_unexpected_error, uiState.errorResourceId)
        Assert.assertEquals(false, uiState.isLoading)

        /*
        * when the use case fails due to REQUEST_TIMEOUT error
        * */


        repositoryError = RepositoryResponse.Error(HttpResponseErrorCode.REQUEST_TIMEOUT)
        `when`(publicApisSourceRepositoryMock.getEntries()).thenReturn(repositoryError)

        viewModel.getApisList()
        uiState = viewModel.uiState.first()

        Assert.assertEquals(R.string.network_timeout, uiState.errorResourceId)
        Assert.assertEquals(false, uiState.isLoading)

    }


    @Test
    fun `apis list request should success`() = testScope.runBlockingTest {
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
        val repositoryResponse = RepositoryResponse.Success(mockList)
        `when`(publicApisSourceRepositoryMock.getEntries()).thenReturn(repositoryResponse)
        viewModel.getApisList()
        val uiState = viewModel.uiState.first()

        Assert.assertEquals(null, uiState.errorResourceId)
        Assert.assertEquals(false, uiState.isLoading)
        Assert.assertEquals(mockList, uiState.apisList)
    }
}