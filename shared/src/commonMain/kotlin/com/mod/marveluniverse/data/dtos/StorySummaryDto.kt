package com.mod.marveluniverse.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class StorySummaryDto(
    val resourceURI: String,
    val name: String,
    val type: String
)
