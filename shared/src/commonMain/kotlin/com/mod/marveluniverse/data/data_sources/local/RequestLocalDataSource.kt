package com.mod.marveluniverse.data.data_sources.local

import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.RequestType
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.utils.EncryptionHelper
import database.RequestEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

interface RequestLocalDataSource {
    suspend fun getRequest(
        type: RequestType,
        resourceType: ResourceType,
        resourceId: Int? = null,
        relatedEntity: ResourceType? = null,
        relatedEntityId: Int? = null,
        query: String? = null,
        sort: Sort? = null,
        limit: Int? = null,
        offset: Int? = null
    ): RequestEntity?

    suspend fun getRequestCode(
        type: RequestType,
        resourceType: ResourceType,
        query: String? = null,
        sort: Sort? = null,
    ): String?

    suspend fun insertRequest(
        id: Int? = null,
        code: String? = null,
        type: RequestType,
        resourceType: ResourceType,
        resourceId: Int? = null,
        relatedEntity: ResourceType? = null,
        relatedEntityId: Int? = null,
        query: String? = null,
        sort: Sort? = null,
        totalResults: Int,
        limit: Int? = null,
        offset: Int? = null,
        etag: String,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime,
    ): String

    suspend fun refreshRequest(request: RequestEntity)

    suspend fun deleteRequestsByCode(code: String)
}

class RequestLocalDataSourceImpl(
    db: MarvelUniverseDatabase
) : RequestLocalDataSource {
    private val queries = db.requestQueries

    override suspend fun getRequest(
        type: RequestType,
        resourceType: ResourceType,
        resourceId: Int?,
        relatedEntity: ResourceType?,
        relatedEntityId: Int?,
        query: String?,
        sort: Sort?,
        limit: Int?,
        offset: Int?
    ): RequestEntity? {
        return queries
            .getRequest(
                type, resourceType, resourceId, relatedEntity, relatedEntityId, query, sort, limit, offset
            )
            .executeAsOneOrNull()
    }

    override suspend fun getRequestCode(
        type: RequestType,
        resourceType: ResourceType,
        query: String?,
        sort: Sort?
    ): String? {
        return queries
            .getRequestCode(
                type, resourceType, query, sort
            ).executeAsOneOrNull()
    }

    override suspend fun insertRequest(
        id: Int?,
        code: String?,
        type: RequestType,
        resourceType: ResourceType,
        resourceId: Int?,
        relatedEntity: ResourceType?,
        relatedEntityId: Int?,
        query: String?,
        sort: Sort?,
        totalResults: Int,
        limit: Int?,
        offset: Int?,
        etag: String,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime
    ): String {
        val generatedCode = code ?: EncryptionHelper.md5("${type.name}-${resourceType.name}-${query}-${sort?.name}")

        queries.insertRequest(
            id,
            generatedCode,
            type,
            resourceType,
            resourceId,
            relatedEntity,
            relatedEntityId,
            query,
            sort,
            totalResults,
            limit,
            offset,
            etag,
            createdAt,
            updatedAt
        )

        return generatedCode
    }

    override suspend fun refreshRequest(request: RequestEntity) {
        insertRequest(
            id = request.id,
            code = request.code,
            type = request.type,
            resourceType = request.resourceType,
            resourceId = request.resourceId,
            query = request.query,
            sort = request.sort,
            totalResults = request.totalResults,
            limit = request.recordsLimit,
            offset = request.offset,
            etag = request.etag,
            createdAt = request.createdAt,
            updatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )
    }

    override suspend fun deleteRequestsByCode(code: String) {
        queries.deleteRequestsByCode(code)
    }
}