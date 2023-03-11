package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.dtos.EventDto
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.entites.ResourceType
import database.event.EventEntity
import database.event.EventResourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface EventLocalDataSource {
    suspend fun getEvents(): Flow<List<EventEntity>>
    suspend fun getEventsByRequestCode(requestCode: String): Flow<List<EventEntity>>
    suspend fun getEventById(id: Int): EventEntity
    suspend fun getEventsByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<EventResourceEntity>>

    suspend fun insertEvents(
        requestCode: String,
        events: List<EventDto>
    )

    suspend fun insertEventsResource(
        resourceType: ResourceType,
        resourceId: Int,
        events: List<EventDto>
    )

    suspend fun clearEventsByRequestCode(requestCode: String)
    suspend fun clearEventsByRemoteIdsAndRequestCode(remoteIds: List<Int>, requestCode: String)
    suspend fun clearEventsResource(resourceType: ResourceType, resourceId: Int)
}

class EventLocalDataSourceImpl(
    db: MarvelUniverseDatabase
) : EventLocalDataSource {
    private val eventQueries = db.eventQueries
    private val eventResourceQueries = db.event_resourceQueries

    override suspend fun getEvents(): Flow<List<EventEntity>> {
        return eventQueries
            .getEvents()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override suspend fun getEventsByRequestCode(requestCode: String): Flow<List<EventEntity>> {
        return eventQueries
            .getEventsByRequestCode(requestCode)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override suspend fun getEventById(id: Int): EventEntity {
        return eventQueries
            .getEventByRemoteId(id)
            .executeAsOne()
    }

    override suspend fun getEventsByResource(
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

    override suspend fun insertEvents(
        requestCode: String,
        events: List<EventDto>
    ) {
        eventQueries.transaction {
            events.forEach {
                eventQueries
                    .insertEvent(
                        id = null,
                        requestCode = requestCode,
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

    override suspend fun insertEventsResource(
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

    override suspend fun clearEventsByRequestCode(requestCode: String) {
        eventQueries.clearRequestEvents(requestCode)
    }

    override suspend fun clearEventsByRemoteIdsAndRequestCode(
        remoteIds: List<Int>,
        requestCode: String
    ) {
        eventQueries.clearEventsByRemoteIdsAndRequestCode(remoteIds, requestCode)
    }

    override suspend fun clearEventsResource(resourceType: ResourceType, resourceId: Int) {
        eventResourceQueries.clearEventsResource(
            resourceType = resourceType,
            resourceId = resourceId
        )
    }
}