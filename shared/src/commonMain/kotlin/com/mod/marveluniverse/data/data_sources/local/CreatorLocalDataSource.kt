package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.dtos.*
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.entites.ResourceType
import database.creator.CreatorEntity
import database.creator.CreatorResourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface CreatorLocalDataSource {
    fun getCreators(): Flow<List<CreatorEntity>>
    fun getCreatorById(id: Int): CreatorEntity
    fun getCreatorsByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<CreatorResourceEntity>>

    fun insertCreators(
        creators: List<CreatorDto>
    )

    fun insertCreatorsResource(
        resourceType: ResourceType,
        resourceId: Int,
        creators: List<CreatorDto>
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
            .getCreatorByRemoteId(id)
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

    override fun insertCreators(
        creators: List<CreatorDto>
    ) {
        creatorQueries.transaction {
            creators.forEach {
                creatorQueries
                    .insertCreator(
                        id = null,
                        remoteId = it.id,
                        firstName = it.firstName,
                        middleName = it.middleName,
                        lastName = it.lastName,
                        suffix = it.suffix,
                        fullName = it.fullName,
                        modified = it.modified,
                        urls = it.urls,
                        thumbnail = it.thumbnail,
                        comics = it.comics,
                        series = it.series,
                        stories = it.stories,
                        events = it.events
                    )
            }
        }
    }

    override fun insertCreatorsResource(
        resourceType: ResourceType,
        resourceId: Int,
        creators: List<CreatorDto>
    ) {
        creatorResourceQueries.transaction {
            creators.forEach {
                creatorResourceQueries
                    .insertCreatorResource(
                        id = null,
                        resourceType = resourceType,
                        resourceId = resourceId,
                        remoteId = it.id,
                        firstName = it.firstName,
                        middleName = it.middleName,
                        lastName = it.lastName,
                        suffix = it.suffix,
                        fullName = it.fullName,
                        modified = it.modified,
                        urls = it.urls,
                        thumbnail = it.thumbnail,
                        comics = it.comics,
                        series = it.series,
                        stories = it.stories,
                        events = it.events
                    )
            }
        }
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