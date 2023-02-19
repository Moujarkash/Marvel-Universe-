package com.mod.marveluniverse.domain.entites

data class StoriesResourceList(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<StorySummary>
)
