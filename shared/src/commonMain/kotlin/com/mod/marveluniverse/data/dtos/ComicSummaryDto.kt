package com.mod.marveluniverse.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ComicSummaryDto(
    val resourceURI: String,
    val name: String
)
