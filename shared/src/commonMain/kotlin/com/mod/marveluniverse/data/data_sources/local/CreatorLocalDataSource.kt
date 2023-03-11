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
    suspend fun getCreators(): Flow<List<CreatorEntity>>
    suspend fun getCreatorsByRequestCode(requestCode: String): Flow<List<CreatorEntity>>

    suspend fun getCreatorById(id: Int): CreatorEntity
    suspend fun getCreatorsByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<CreatorResourceEntity>>

    suspend fun insertCreators(
        requestCode: String,
        creators: List<CreatorDto>
    )

    suspend fun insertCreatorsResource(
        resourceType: ResourceType,
        resourceId: Int,
        creators: List<CreatorDto>
    )

    suspend fun clearCreatorsByRequestCode(requestCode: String)
    suspend fun clearCreatorsByRemoteIdsAndRequestCode(remoteIds: List<Int>, requestCode: String)
    suspend fun clearCreatorsResource(resourceType: ResourceType, resourceId: Int)
}

class CreatorLocalDataSourceImpl(
    db: MarvelUniverseDatabase
) : CreatorLocalDataSource {
    private val creatorQueries = db.creatorQueries
    private val creatorResourceQueries = db.creator_resourceQueries

    override suspend fun getCreators(): Flow<List<CreatorEntity>> {
        return creatorQueries
            .getCreators()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override suspend fun getCreatorsByRequestCode(requestCode: String): Flow<List<CreatorEntity>> {
        return creatorQueries
            .getCreatorsByRequestCode(requestCode)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override suspend fun getCreatorById(id: Int): CreatorEntity {
        return creatorQueries
            .getCreatorByRemoteId(id)
            .executeAsOne()
    }

    override suspend fun getCreatorsByResource(
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

    override suspend fun insertCreators(
        requestCode: String,
        creators: List<CreatorDto>
    ) {
        creatorQueries.transaction {
            creators.forEach {
                creatorQueries
                    .insertCreator(
                        id = null,
                        requestCode = requestCode,
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

    override suspend fun insertCreatorsResource(
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

    override suspend fun clearCreatorsByRequestCode(requestCode: String) {
        creatorQueries.clearRequestCreators(requestCode)
    }

    override suspend fun clearCreatorsByRemoteIdsAndRequestCode(
        remoteIds: List<Int>,
        requestCode: String
    ) {
        creatorQueries.clearCreatorsByRemoteIdsAndRequestCode(remoteIds, requestCode)
    }

    override suspend fun clearCreatorsResource(resourceType: ResourceType, resourceId: Int) {
        creatorResourceQueries.clearCreatorsResource(
            resourceType = resourceType,
            resourceId = resourceId
        )
    }

}