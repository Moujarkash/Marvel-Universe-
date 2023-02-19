package com.mod.marveluniverse.domain.entites

data class SeriesResourceList(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<SeriesSummary>
)
