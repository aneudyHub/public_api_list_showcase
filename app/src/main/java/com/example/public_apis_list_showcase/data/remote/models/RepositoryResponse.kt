package com.example.public_apis_list_showcase.data.remote.models

sealed class RepositoryResponse<out T> {
    data class Success<out T>(val data: T) : RepositoryResponse<T>()
    data class Error(val error: HttpResponseErrorCode) : RepositoryResponse<Nothing>()
}