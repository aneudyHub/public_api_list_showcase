package com.example.public_apis_list_showcase.data.repositories

import com.example.public_apis_list_showcase.data.remote.models.Entry
import com.example.public_apis_list_showcase.data.remote.models.HttpResponseErrorCode
import com.example.public_apis_list_showcase.data.remote.models.PublicApisSourceResponse
import com.example.public_apis_list_showcase.data.remote.models.RepositoryResponse
import com.example.public_apis_list_showcase.di.AppModule
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@RunWith(JUnit4::class)
@HiltAndroidTest
@UninstallModules(AppModule::class)
class PublicApisSourceRepositoryTest {
    @Inject
    lateinit var publicApisSourceRepository: PublicApisSourceRepository

    private val mockWebServer = MockWebServer()
    private val serverPort = 8080

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val gson = Gson()

    @Before
    fun setup() {
        hiltRule.inject()
        mockWebServer.start(serverPort)
    }

    @Test
    fun getEntriesListShouldFailWithBadRequest() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(400))
        val response = publicApisSourceRepository.getEntries()
        val errorResponse = response as RepositoryResponse.Error
        Assert.assertEquals(errorResponse.error, HttpResponseErrorCode.BAD_REQUEST)
    }

    @Test
    fun getEntriesListShouldFailWithServerTimeOut() = runTest {
        val serverResponse =
            MockResponse().setResponseCode(408).setHeadersDelay(4, TimeUnit.SECONDS);
        mockWebServer.enqueue(serverResponse)

        val response = publicApisSourceRepository.getEntries()
        val errorResponse = response as RepositoryResponse.Error
        Assert.assertEquals(HttpResponseErrorCode.REQUEST_TIMEOUT, errorResponse.error)
    }

    @Test
    fun getEntriesListShouldSuccess() = runTest {
        val mockResponse = PublicApisSourceResponse(
            count = 1,
            entries = mutableListOf<Entry>().apply {
                this.add(
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

            }
        )
        val serverResponse = MockResponse().setResponseCode(200).setBody(gson.toJson(mockResponse))
        mockWebServer.enqueue(serverResponse)
        val response = publicApisSourceRepository.getEntries()
        val entriesList = (response as RepositoryResponse.Success).data
        Assert.assertNotEquals(0, entriesList.size)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}