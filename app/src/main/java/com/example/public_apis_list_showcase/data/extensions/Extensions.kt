package com.example.public_apis_list_showcase.data.extensions

import com.example.public_apis_list_showcase.data.remote.models.HttpResponseErrorCode
import retrofit2.Response


fun <T> Response<T>.handleErrorResponse(): HttpResponseErrorCode {
    val error = when (code()) {
        400 -> HttpResponseErrorCode.BAD_REQUEST
        404 -> HttpResponseErrorCode.NOT_FOUND
        500 -> HttpResponseErrorCode.INTERNAL_SERVER_ERROR
        408 -> HttpResponseErrorCode.REQUEST_TIMEOUT
        else -> HttpResponseErrorCode.UNKNOWN
    }
    return error
}