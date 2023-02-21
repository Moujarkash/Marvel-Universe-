package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.dtos.*
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.entites.ResourceType
import database.creator.CreatorEntity
import database.creator.CreatorResourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

interface CreatorLocalDataSource {
    fun getCreators(): Flow<List<CreatorEntity>>
    fun getCreatorById(id: Int): CreatorEntity
    fun getCreatorsByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<CreatorResourceEntity>>

    fun insertCreator(
        id: Int,
        firstName: String,
        middleName: String,
        lastName: String,
        suffix: String,
        fullName: String,
        modified: LocalDateTime,
        urls: List<UrlDto>,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto
    )

    fun insertCreatorResource(
        resourceType: ResourceType,
        resourceId: Int,
        id: Int,
        firstName: String,
        middleName: String,
        lastName: String,
        suffix: String,
        fullName: String,
        modified: LocalDateTime,
        urls: List<UrlDto>,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto
    )

    fun clearCreators()
    fun clearCreatorsResource(resourceType: ResourceType, resourceId: Int)
}

class CreatorLocalDataSourceImpl(
    db: MarvelUniverseDatabase
) : CreatorLocalDataSource {
    private val creatorQueries = db.creatorQueries
    private val creatorResourceQueries = db.creator_resourceQueries

    override fun getCreators(): Flow<List<CreatorEntity>> {
        return creatorQueries
            .getCreators()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override fun getCreatorById(id: Int): CreatorEntity {
        return creatorQueries
            .getCreatorById(id)
            .executeAsOne()
    }

    override fun getCreatorsByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<CreatorResourceEntity>> {
        return creatorResourceQueries
            .getCreatorsResourceByType(resourceType, resourceId)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override fun insertCreator(
        id: Int,
        firstName: String,
        middleName: String,
        lastName: String,
        suffix: String,
        fullName: String,
        modified: LocalDateTime,
        urls: List<UrlDto>,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto
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
                urls = urls,
                thumbnail = thumbnail,
                comics = comics,
                series = series,
                stories = stories,
                events = events
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
        urls: List<UrlDto>,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto
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
                urls = urls,
                thumbnail = thumbnail,
                comics = comics,
                series = series,
                stories = stories,
                events = events
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