package com.mod.marveluniverse.domain.entites

data class ResourceList<T>(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<T>
)
