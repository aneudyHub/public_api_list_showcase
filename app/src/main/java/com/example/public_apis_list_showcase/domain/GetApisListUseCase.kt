package com.example.public_apis_list_showcase.domain

import com.example.public_apis_list_showcase.data.remote.models.Entry
import com.example.public_apis_list_showcase.data.remote.models.HttpResponseErrorCode
import com.example.public_apis_list_showcase.data.remote.models.RepositoryResponse
import com.example.public_apis_list_showcase.data.repositories.PublicApisSourceRepository
import com.example.public_apis_list_showcase.domain.models.UseCaseErrorType
import com.example.public_apis_list_showcase.domain.models.UseCaseResult
import javax.inject.Inject

class GetApisListUseCase @Inject constructor(
    private val publicApisSourceRepository: PublicApisSourceRepository
) {

    suspend operator fun invoke(filter: String): UseCaseResult<List<Entry>> {
        return try {
            when (val entries = publicApisSourceRepository.getEntries()) {
                is RepositoryResponse.Success -> {
                    val result = entries.data.filter { entry ->
                        entry.api.contains(filter) || entry.description.contains(filter)
                    }
                    UseCaseResult.Success(result)
                }

                is RepositoryResponse.Error -> {
                    UseCaseResult.Error(handleErrorResponse(entries.error))
                }
            }
        } catch (e: Exception) {
            UseCaseResult.Error(UseCaseErrorType.THROWN_EXCEPTION)
        }
    }

    private fun handleErrorResponse(httpResponseErrorCode: HttpResponseErrorCode): UseCaseErrorType {
        return when (httpResponseErrorCode) {
            HttpResponseErrorCode.BAD_REQUEST,
            HttpResponseErrorCode.INTERNAL_SERVER_ERROR,
            HttpResponseErrorCode.REQUEST_TIMEOUT -> UseCaseErrorType.SERVER_ERROR

            HttpResponseErrorCode.UNKNOWN, HttpResponseErrorCode.THROWN_EXCEPTION -> UseCaseErrorType.THROWN_EXCEPTION

            HttpResponseErrorCode.NOT_FOUND -> UseCaseErrorType.API_REQUEST_NOT_FOUND
        }
    }
}