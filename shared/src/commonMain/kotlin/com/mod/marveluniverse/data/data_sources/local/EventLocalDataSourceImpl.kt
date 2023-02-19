package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.mappers.toDataDto
import com.mod.marveluniverse.data.mappers.toDomainEntity
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.data_sources.local.EventLocalDataSource
import com.mod.marveluniverse.domain.entites.*
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import com.mod.marveluniverse.domain.utils.flows.toCommonFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

class EventLocalDataSourceImpl(
    db: MarvelUniverseDatabase
): EventLocalDataSource {
    private val eventQueries = db.eventQueries
    private val eventResourceQueries = db.event_resourceQueries

    override fun getEvents(): CommonFlow<List<Event>> {
        return eventQueries
            .getEvents()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
            .map { comics ->
                comics.map { it.toDomainEntity() }
            }
            .toCommonFlow()
    }

    override fun getEventById(id: Int): Event {
        return eventQueries
            .getEventById(id)
            .executeAsOne()
            .toDomainEntity()
    }

    override fun getEventsByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): CommonFlow<List<Event>> {
        return eventResourceQueries
            .getEventsResourceByType(resourceType, resourceId)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
            .map { comics ->
                comics.map { it.toDomainEntity() }
            }
            .toCommonFlow()
    }

    override fun insertEvent(
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
    ) {
       eventQueries
           .insertEvent(
               id = id,
               title = title,
               description = description,
               urls = urls.map { it.toDataDto() },
               modified = modified,
               start = start,
               end = end,
               thumbnail = thumbnail.toDataDto(),
               comics = comics.toDataDto(),
               series = series.toDataDto(),
               stories = stories.toDataDto(),
               characters = characters.toDataDto(),
               creators = creators.toDataDto(),
               next = next?.toDataDto(),
               previous = previous?.toDataDto()
           )
    }

    override fun insertEventResource(
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
    ) {
        eventResourceQueries
            .insertEventResource(
                resourceType = resourceType,
                resourceId = resourceId,
                id = id,
                title = title,
                description = description,
                urls = urls.map { it.toDataDto() },
                modified = modified,
                start = start,
                end = end,
                thumbnail = thumbnail.toDataDto(),
                comics = comics.toDataDto(),
                series = series.toDataDto(),
                stories = stories.toDataDto(),
                characters = characters.toDataDto(),
                creators = creators.toDataDto(),
                next = next?.toDataDto(),
                previous = previous?.toDataDto()
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