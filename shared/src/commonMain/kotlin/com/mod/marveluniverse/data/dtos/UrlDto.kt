package com.mod.marveluniverse.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class UrlDto(
    val type: String,
    val url: String
)
