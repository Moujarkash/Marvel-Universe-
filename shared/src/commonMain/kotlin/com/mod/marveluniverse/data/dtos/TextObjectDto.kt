package com.mod.marveluniverse.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class TextObjectDto(
    val type: String,
    val language: String,
    val text: String
)
