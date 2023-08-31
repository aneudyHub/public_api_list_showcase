package com.example.public_apis_list_showcase.domain.models

sealed class UseCaseResult<out T> {
    data class Success<out T>(val data: T) : UseCaseResult<T>()
    data class Error(val errorType: UseCaseErrorType) : UseCaseResult<Nothing>()
}