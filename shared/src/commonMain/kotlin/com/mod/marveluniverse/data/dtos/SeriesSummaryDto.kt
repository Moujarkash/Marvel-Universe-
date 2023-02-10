package com.mod.marveluniverse.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class SeriesSummaryDto(
    val resourceURI: String,
    val name: String
)
