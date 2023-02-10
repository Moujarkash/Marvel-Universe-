package com.mod.marveluniverse.data.data_sources

import com.mod.marveluniverse.data.dtos.ErrorDto
import com.mod.marveluniverse.domain.error.AppException
import com.mod.marveluniverse.domain.error.ErrorType
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.utils.io.errors.*

abstract class BaseRemoteDataSource {
    suspend fun <T> processRequest(request: suspend () -> HttpResponse, onSuccess: suspend (HttpResponse) -> T): T {
        val response = try {
            request()
        } catch (e: IOException) {
            throw AppException(errorType = ErrorType.SERVICE_UNAVAILABLE)
        }

        when (response.status.value) {
            in 200..299 -> Unit
            500 -> throw AppException(errorType = ErrorType.SERVER_ERROR)
            in 400..499 -> {
                val serverError = response.body<ErrorDto>()
                throw AppException(errorType = ErrorType.CLIENT_ERROR, errorMessage = serverError.message)
            }
            else -> throw AppException(errorType = ErrorType.UNKNOWN_ERROR)
        }

        try {
            return onSuccess(response)
        } catch (e: Exception) {
            throw AppException(errorType = ErrorType.SERVER_ERROR)
        }
    }
}