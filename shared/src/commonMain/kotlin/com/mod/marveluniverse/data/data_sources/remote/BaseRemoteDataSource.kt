package com.mod.marveluniverse.data.data_sources.remote

import com.mod.marveluniverse.data.dtos.ErrorDto
import com.mod.marveluniverse.domain.error.AppException
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.utils.io.errors.*

abstract class BaseRemoteDataSource {
    suspend fun <T> processRequest(request: suspend () -> HttpResponse, onSuccess: suspend (HttpResponse) -> T): T {
        val response = try {
            request()
        } catch (e: IOException) {
            throw AppException.ServiceUnAvailable
        }

        when (response.status.value) {
            in 200..299 -> Unit
            500 -> throw AppException.ServerError()
            in 400..499 -> {
                val serverError = response.body<ErrorDto>()
                throw AppException.ClientError(clientErrorMessage = serverError.message)
            }
            304 -> throw AppException.DataNotChangedOnServer
            else -> throw AppException.UnknownError
        }

        try {
            return onSuccess(response)
        } catch (e: Exception) {
            throw AppException.ServerError()
        }
    }
}