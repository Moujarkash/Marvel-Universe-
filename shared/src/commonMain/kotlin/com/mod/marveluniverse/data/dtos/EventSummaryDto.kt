package com.mod.marveluniverse.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class EventSummaryDto(
    val resourceURI: String,
    val name: String
)
