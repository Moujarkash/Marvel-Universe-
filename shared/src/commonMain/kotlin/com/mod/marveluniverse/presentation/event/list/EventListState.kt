package com.mod.marveluniverse.presentation.event.list

import com.mod.marveluniverse.domain.entites.Event
import com.mod.marveluniverse.domain.entites.Sort

data class EventListState(
    val events: List<Event> = emptyList(),
    val isFetchingEvents: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val query: String? = null,
    val sort: Sort = Sort.ASCENDING
)
