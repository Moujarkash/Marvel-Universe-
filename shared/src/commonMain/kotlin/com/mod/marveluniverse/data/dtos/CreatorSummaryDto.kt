package com.mod.marveluniverse.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CreatorSummaryDto(
    val resourceURI: String,
    val name: String,
    val role: String
)
