package com.mod.marveluniverse.domain.error

class AppException(
    val errorType: ErrorType,
    val errorMessage: String = "An error occurred."
): Exception("type: $errorType, message: $errorMessage")