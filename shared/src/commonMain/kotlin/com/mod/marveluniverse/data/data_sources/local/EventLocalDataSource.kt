package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.dtos.*
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.entites.ResourceType
import database.event.EventEntity
import database.event.EventResourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface EventLocalDataSource {
    fun getEvents(): Flow<List<EventEntity>>
    fun getEventById(id: Int): EventEntity
    fun getEventsByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<EventResourceEntity>>

    fun insertEvents(
        events: List<EventDto>
    )

    fun insertEventsResource(
        resourceType: ResourceType,
        resourceId: Int,
        events: List<EventDto>
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
            .getEventByRemoteId(id)
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

    override fun insertEvents(
        events: List<EventDto>
    ) {
        eventQueries.transaction {
            events.forEach {
                eventQueries
                    .insertEvent(
                        id = null,
                        remoteId = it.id,
                        title = it.title,
                        description = it.description,
                        urls = it.urls,
                        modified = it.modified,
                        start = it.start,
                        end = it.end,
                        thumbnail = it.thumbnail,
                        comics = it.comics,
                        series = it.series,
                        stories = it.stories,
                        characters = it.characters,
                        creators = it.creators,
                        next = it.next,
                        previous = it.previous
                    )
            }
        }
    }

    override fun insertEventsResource(
        resourceType: ResourceType,
        resourceId: Int,
        events: List<EventDto>
    ) {
        eventResourceQueries.transaction {
            events.forEach {
                eventResourceQueries
                    .insertEventResource(
                        id = null,
                        resourceType = resourceType,
                        resourceId = resourceId,
                        remoteId = it.id,
                        title = it.title,
                        description = it.description,
                        urls = it.urls,
                        modified = it.modified,
                        start = it.start,
                        end = it.end,
                        thumbnail = it.thumbnail,
                        comics = it.comics,
                        series = it.series,
                        stories = it.stories,
                        characters = it.characters,
                        creators = it.creators,
                        next = it.next,
                        previous = it.previous
                    )
            }
        }
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