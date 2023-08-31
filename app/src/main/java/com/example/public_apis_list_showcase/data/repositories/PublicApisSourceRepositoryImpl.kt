package com.example.public_apis_list_showcase.data.repositories

import com.example.public_apis_list_showcase.data.extensions.handleErrorResponse
import com.example.public_apis_list_showcase.data.remote.api.ApiService
import com.example.public_apis_list_showcase.data.remote.models.Entry
import com.example.public_apis_list_showcase.data.remote.models.HttpResponseErrorCode
import com.example.public_apis_list_showcase.data.remote.models.RepositoryResponse
import java.lang.Exception
import java.net.SocketTimeoutException
import javax.inject.Inject

class PublicApisSourceRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PublicApisSourceRepository {
    override suspend fun getEntries(): RepositoryResponse<List<Entry>> {
        return try {
            val response = apiService.getEntries()
            if (response.isSuccessful) {
                val entries = response.body()!!.entries
                return RepositoryResponse.Success(entries)
            } else {
                val error = response.handleErrorResponse()
                RepositoryResponse.Error(error)
            }
        } catch (timeout: SocketTimeoutException) {
            return RepositoryResponse.Error(HttpResponseErrorCode.REQUEST_TIMEOUT)
        } catch (e: Exception) {
            return RepositoryResponse.Error(HttpResponseErrorCode.THROWN_EXCEPTION)
        }
    }
}