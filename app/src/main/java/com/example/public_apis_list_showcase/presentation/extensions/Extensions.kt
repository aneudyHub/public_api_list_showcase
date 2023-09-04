package com.example.public_apis_list_showcase.presentation.extensions

import com.example.public_apis_list_showcase.R
import com.example.public_apis_list_showcase.domain.models.UseCaseErrorType


fun UseCaseErrorType.getResourceId(): Int {
    return when (this) {
        UseCaseErrorType.THROWN_EXCEPTION, UseCaseErrorType.API_REQUEST_NOT_FOUND -> R.string.network_unexpected_error
        UseCaseErrorType.SERVER_ERROR -> R.string.network_server_error
        UseCaseErrorType.REQUEST_TIMEOUT -> R.string.network_timeout
    }
}