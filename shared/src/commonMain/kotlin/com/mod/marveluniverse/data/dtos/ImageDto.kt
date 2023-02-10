package com.mod.marveluniverse.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    val path: String,
    val extension: String
)
