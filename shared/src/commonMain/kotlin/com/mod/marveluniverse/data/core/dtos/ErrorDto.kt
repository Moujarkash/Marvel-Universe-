package com.mod.marveluniverse.data.core.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
    val code: String = "",
    val message: String = "Server Error"
)
