package com.mod.marveluniverse.domain.entites

data class EventsResourceList(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<EventSummary>
)
