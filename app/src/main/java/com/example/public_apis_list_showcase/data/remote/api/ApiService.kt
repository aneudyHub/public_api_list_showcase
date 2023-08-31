package com.example.public_apis_list_showcase.data.remote.api

import com.example.public_apis_list_showcase.data.remote.models.PublicApisSourceResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/entries")
    suspend fun getEntries(): Response<PublicApisSourceResponse>
}