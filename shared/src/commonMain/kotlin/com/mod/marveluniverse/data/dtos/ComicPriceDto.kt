package com.mod.marveluniverse.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ComicPriceDto(
    val type: String,
    val price: Double
)
