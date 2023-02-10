package com.mod.marveluniverse.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ResourceListDto<T>(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<T>
)
