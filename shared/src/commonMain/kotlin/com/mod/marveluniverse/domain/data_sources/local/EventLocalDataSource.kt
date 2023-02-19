package com.mod.marveluniverse.domain.data_sources.local

import com.mod.marveluniverse.domain.entites.*
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import kotlinx.datetime.LocalDateTime

interface EventLocalDataSource {
    fun getEvents(): CommonFlow<List<Event>>
    fun getEventById(id: Int): Event
    fun getEventsByResource(resourceType: ResourceType, resourceId: Int): CommonFlow<List<Event>>
    fun insertEvent(
        id: Int,
        title: String,
        description: String,
        urls: List<Url>,
        modified: LocalDateTime,
        start: LocalDateTime,
        end: LocalDateTime,
        thumbnail: Image,
        comics: ComicsResourceList,
        series: SeriesResourceList,
        stories: StoriesResourceList,
        characters: CharactersResourceList,
        creators: CreatorsResourceList,
        next: EventSummary?,
        previous: EventSummary?
    )
    fun insertEventResource(
        resourceType: ResourceType,
        resourceId: Int,
        id: Int,
        title: String,
        description: String,
        urls: List<Url>,
        modified: LocalDateTime,
        start: LocalDateTime,
        end: LocalDateTime,
        thumbnail: Image,
        comics: ComicsResourceList,
        series: SeriesResourceList,
        stories: StoriesResourceList,
        characters: CharactersResourceList,
        creators: CreatorsResourceList,
        next: EventSummary?,
        previous: EventSummary?
    )
    fun clearEvents()
    fun clearEventsResource(resourceType: ResourceType, resourceId: Int)
}