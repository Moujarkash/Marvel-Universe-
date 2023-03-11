package com.mod.marveluniverse.presentation.event.list

import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.Sort

sealed class EventListEvent {
    object RequestEvents: EventListEvent()
    object RefreshEvents: EventListEvent()
    data class DrawerItemClicked(val resourceType: ResourceType): EventListEvent()
    data class SearchEvents(val query: String): EventListEvent()
    object SearchTextCleared: EventListEvent()
    data class SortEvents(val sort: Sort): EventListEvent()
}
