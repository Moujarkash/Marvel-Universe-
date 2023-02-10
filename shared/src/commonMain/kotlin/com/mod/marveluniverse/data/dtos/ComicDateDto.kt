package com.mod.marveluniverse.data.dtos

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ComicDateDto(
    val type: String,
    val date: LocalDateTime
)
