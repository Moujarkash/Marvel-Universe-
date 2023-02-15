package com.mod.marveluniverse.data.data_sources

import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.data_sources.RequestDataSource
import com.mod.marveluniverse.domain.entites.EntityType
import com.mod.marveluniverse.domain.entites.RequestType
import database.RequestEntity
import kotlinx.datetime.LocalDateTime

class SqlDelightRequestDataSource(
    db: MarvelUniverseDatabase
) : RequestDataSource {
    private val queries = db.requestQueries

    override fun getRequest(
        type: RequestType,
        entity: EntityType,
        entityId: Int?,
        relatedEntity: String?,
        query: String?
    ): RequestEntity {
        return queries
            .getRequest(
                type, entity, entityId, relatedEntity, query
            )
            .executeAsOne()
    }

    override suspend fun insertRequest(
        id: Int?,
        type: RequestType,
        entity: EntityType,
        entityId: Int?,
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
            entity,
            entityId,
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