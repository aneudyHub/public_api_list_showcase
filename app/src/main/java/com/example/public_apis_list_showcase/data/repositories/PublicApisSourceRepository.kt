package com.example.public_apis_list_showcase.data.repositories

import com.example.public_apis_list_showcase.data.remote.models.Entry
import com.example.public_apis_list_showcase.data.remote.models.RepositoryResponse

interface PublicApisSourceRepository {
    suspend fun getEntries(): RepositoryResponse<List<Entry>>
}