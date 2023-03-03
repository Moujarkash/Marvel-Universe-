package com.mod.marveluniverse.data.data_sources.local

import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.RequestType
import database.RequestEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

interface RequestLocalDataSource {
    fun getRequest(
        type: RequestType,
        resourceType: ResourceType,
        resourceId: Int? = null,
        relatedEntity: String? = null,
        query: String? = null,
        limit: Int? = null,
        offset: Int? = null
    ): RequestEntity?

    fun getRequestsNotMatchingQuery(
        type: RequestType,
        resourceType: ResourceType,
        query: String? = null
    ): List<RequestEntity>

    suspend fun insertRequest(
        id: Int? = null,
        type: RequestType,
        resourceType: ResourceType,
        resourceId: Int? = null,
        relatedEntity: String? = null,
        query: String? = null,
        totalResults: Int,
        limit: Int? = null,
        offset: Int? = null,
        etag: String,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime,
    )

    suspend fun refreshRequest(request: RequestEntity)

    fun deleteRequestsByTypeAndResource(type: RequestType, resourceType: ResourceType)
}

class RequestLocalDataSourceImpl(
    db: MarvelUniverseDatabase
) : RequestLocalDataSource {
    private val queries = db.requestQueries

    override fun getRequest(
        type: RequestType,
        resourceType: ResourceType,
        resourceId: Int?,
        relatedEntity: String?,
        query: String?,
        limit: Int?,
        offset: Int?
    ): RequestEntity? {
        return queries
            .getRequest(
                type, resourceType, resourceId, relatedEntity, query, limit, offset
            )
            .executeAsOneOrNull()
    }

    override fun getRequestsNotMatchingQuery(
        type: RequestType,
        resourceType: ResourceType,
        query: String?
    ): List<RequestEntity> {
        return queries
            .getRequestsNotMatchingQuery(type, resourceType, query)
            .executeAsList()
    }

    override suspend fun insertRequest(
        id: Int?,
        type: RequestType,
        resourceType: ResourceType,
        resourceId: Int?,
        relatedEntity: String?,
        query: String?,
        totalResults: Int,
        limit: Int?,
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
            limit,
            offset,
            etag,
            createdAt,
            updatedAt
        )
    }

    override suspend fun refreshRequest(request: RequestEntity) {
        insertRequest(
            id = request.id,
            type = request.type,
            resourceType = request.resourceType,
            resourceId = request.resourceId,
            query = request.query,
            totalResults = request.totalResults,
            limit = request.recordsLimit,
            offset = request.offset,
            etag = request.etag,
            createdAt = request.createdAt,
            updatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )
    }

    override fun deleteRequestsByTypeAndResource(type: RequestType, resourceType: ResourceType) {
        queries.deleteRequestsByTypeAndResource(type, resourceType)
    }
}