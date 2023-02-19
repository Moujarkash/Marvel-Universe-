package com.mod.marveluniverse.data.data_sources.local

import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.data_sources.local.RequestLocalDataSource
import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.RequestType
import database.RequestEntity
import kotlinx.datetime.LocalDateTime

class RequestLocalDataSourceImpl(
    db: MarvelUniverseDatabase
) : RequestLocalDataSource {
    private val queries = db.requestQueries

    override fun getRequest(
        type: RequestType,
        resourceType: ResourceType,
        resourceId: Int?,
        relatedEntity: String?,
        query: String?
    ): RequestEntity {
        return queries
            .getRequest(
                type, resourceType, resourceId, relatedEntity, query
            )
            .executeAsOne()
    }

    override suspend fun insertRequest(
        id: Int?,
        type: RequestType,
        resourceType: ResourceType,
        resourceId: Int?,
        relatedEntity: String?,
        query: String?,
        totalResults: Int,
        offset: Int?,
        etag: String,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime
    ) {
        queries.insertRequest(
            id,
            type,
            resourceType,
            resourceId,
            relatedEntity,
            query,
            totalResults,
            offset,
            etag,
            createdAt,
            updatedAt
        )
    }
}