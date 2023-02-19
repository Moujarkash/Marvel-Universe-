package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.mappers.toDataDto
import com.mod.marveluniverse.data.mappers.toDomainEntity
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.data_sources.local.CreatorLocalDataSource
import com.mod.marveluniverse.domain.entites.*
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import com.mod.marveluniverse.domain.utils.flows.toCommonFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

class CreatorLocalDataSourceImpl(
    db: MarvelUniverseDatabase
): CreatorLocalDataSource {
    private val creatorQueries = db.creatorQueries
    private val creatorResourceQueries = db.creator_resourceQueries

    override fun getCreators(): CommonFlow<List<Creator>> {
        return creatorQueries
            .getCreators()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
            .map { comics ->
                comics.map { it.toDomainEntity() }
            }
            .toCommonFlow()
    }

    override fun getCreatorById(id: Int): Creator {
        return creatorQueries
            .getCreatorById(id)
            .executeAsOne()
            .toDomainEntity()
    }

    override fun getCreatorsByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): CommonFlow<List<Creator>> {
        return creatorResourceQueries
            .getCreatorsResourceByType(resourceType, resourceId)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
            .map { comics ->
                comics.map { it.toDomainEntity() }
            }
            .toCommonFlow()
    }

    override fun insertCreator(
        id: Int,
        firstName: String,
        middleName: String,
        lastName: String,
        suffix: String,
        fullName: String,
        modified: LocalDateTime,
        urls: List<Url>,
        thumbnail: Image,
        comics: ComicsResourceList,
        series: SeriesResourceList,
        stories: StoriesResourceList,
        events: EventsResourceList
    ) {
        creatorQueries
            .insertCreator(
                id = id,
                firstName = firstName,
                middleName = middleName,
                lastName = lastName,
                suffix = suffix,
                fullName = fullName,
                modified = modified,
                urls = urls.map { it.toDataDto() },
                thumbnail = thumbnail.toDataDto(),
                comics = comics.toDataDto(),
                series = series.toDataDto(),
                stories = stories.toDataDto(),
                events = events.toDataDto()
            )
    }

    override fun insertCreatorResource(
        resourceType: ResourceType,
        resourceId: Int,
        id: Int,
        firstName: String,
        middleName: String,
        lastName: String,
        suffix: String,
        fullName: String,
        modified: LocalDateTime,
        urls: List<Url>,
        thumbnail: Image,
        comics: ComicsResourceList,
        series: SeriesResourceList,
        stories: StoriesResourceList,
        events: EventsResourceList
    ) {
        creatorResourceQueries
            .insertCreatorResource(
                resourceType = resourceType,
                resourceId = resourceId,
                id = id,
                firstName = firstName,
                middleName = middleName,
                lastName = lastName,
                suffix = suffix,
                fullName = fullName,
                modified = modified,
                urls = urls.map { it.toDataDto() },
                thumbnail = thumbnail.toDataDto(),
                comics = comics.toDataDto(),
                series = series.toDataDto(),
                stories = stories.toDataDto(),
                events = events.toDataDto()
            )
    }

    override fun clearCreators() {
        creatorQueries.clearCreators()
    }

    override fun clearCreatorsResource(resourceType: ResourceType, resourceId: Int) {
        creatorResourceQueries.clearCreatorsResource(
            resourceType = resourceType,
            resourceId = resourceId
        )
    }

}