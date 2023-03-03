package com.mod.marveluniverse.domain.error

sealed class AppException(
    val errorMessage: String? = null
): Exception(errorMessage ?: "An error occurred.") {
    object ServiceUnAvailable: AppException("Service Unavailable")
    object UnknownError: AppException()
    object DataNotChangedOnServer: AppException()
    object DataNotFound: AppException()
    data class ClientError(val clientErrorMessage: String? = null): AppException(clientErrorMessage)
    data class ServerError(val serverErrorMessage: String? = null): AppException(serverErrorMessage)
}