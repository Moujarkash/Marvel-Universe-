package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.dtos.*
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.entites.ResourceType
import database.event.EventEntity
import database.event.EventResourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

interface EventLocalDataSource {
    fun getEvents(): Flow<List<EventEntity>>
    fun getEventById(id: Int): EventEntity
    fun getEventsByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<EventResourceEntity>>

    fun insertEvent(
        id: Int,
        title: String,
        description: String,
        urls: List<UrlDto>,
        modified: LocalDateTime,
        start: LocalDateTime,
        end: LocalDateTime,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        stories: StoriesResourceListDto,
        characters: CharactersResourceListDto,
        creators: CreatorsResourceListDto,
        next: EventSummaryDto?,
        previous: EventSummaryDto?
    )

    fun insertEventResource(
        resourceType: ResourceType,
        resourceId: Int,
        id: Int,
        title: String,
        description: String,
        urls: List<UrlDto>,
        modified: LocalDateTime,
        start: LocalDateTime,
        end: LocalDateTime,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        stories: StoriesResourceListDto,
        characters: CharactersResourceListDto,
        creators: CreatorsResourceListDto,
        next: EventSummaryDto?,
        previous: EventSummaryDto?
    )

    fun clearEvents()
    fun clearEventsResource(resourceType: ResourceType, resourceId: Int)
}

class EventLocalDataSourceImpl(
    db: MarvelUniverseDatabase
) : EventLocalDataSource {
    private val eventQueries = db.eventQueries
    private val eventResourceQueries = db.event_resourceQueries

    override fun getEvents(): Flow<List<EventEntity>> {
        return eventQueries
            .getEvents()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override fun getEventById(id: Int): EventEntity {
        return eventQueries
            .getEventById(id)
            .executeAsOne()
    }

    override fun getEventsByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<EventResourceEntity>> {
        return eventResourceQueries
            .getEventsResourceByType(resourceType, resourceId)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override fun insertEvent(
        id: Int,
        title: String,
        description: String,
        urls: List<UrlDto>,
        modified: LocalDateTime,
        start: LocalDateTime,
        end: LocalDateTime,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        stories: StoriesResourceListDto,
        characters: CharactersResourceListDto,
        creators: CreatorsResourceListDto,
        next: EventSummaryDto?,
        previous: EventSummaryDto?
    ) {
        eventQueries
            .insertEvent(
                id = id,
                title = title,
                description = description,
                urls = urls,
                modified = modified,
                start = start,
                end = end,
                thumbnail = thumbnail,
                comics = comics,
                series = series,
                stories = stories,
                characters = characters,
                creators = creators,
                next = next,
                previous = previous
            )
    }

    override fun insertEventResource(
        resourceType: ResourceType,
        resourceId: Int,
        id: Int,
        title: String,
        description: String,
        urls: List<UrlDto>,
        modified: LocalDateTime,
        start: LocalDateTime,
        end: LocalDateTime,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        stories: StoriesResourceListDto,
        characters: CharactersResourceListDto,
        creators: CreatorsResourceListDto,
        next: EventSummaryDto?,
        previous: EventSummaryDto?
    ) {
        eventResourceQueries
            .insertEventResource(
                resourceType = resourceType,
                resourceId = resourceId,
                id = id,
                title = title,
                description = description,
                urls = urls,
                modified = modified,
                start = start,
                end = end,
                thumbnail = thumbnail,
                comics = comics,
                series = series,
                stories = stories,
                characters = characters,
                creators = creators,
                next = next,
                previous = previous
            )
    }

    override fun clearEvents() {
        eventQueries.clearEvents()
    }

    override fun clearEventsResource(resourceType: ResourceType, resourceId: Int) {
        eventResourceQueries.clearEventsResource(
            resourceType = resourceType,
            resourceId = resourceId
        )
    }
}