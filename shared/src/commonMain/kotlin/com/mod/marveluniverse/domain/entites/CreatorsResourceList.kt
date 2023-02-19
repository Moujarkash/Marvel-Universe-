package com.mod.marveluniverse.domain.entites

data class CreatorsResourceList(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<CreatorSummary>
)
