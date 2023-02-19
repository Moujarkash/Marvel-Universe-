package com.mod.marveluniverse.domain.entites

data class ComicsResourceList(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<ComicSummary>
)
