package com.mod.marveluniverse.domain.data_sources

import com.mod.marveluniverse.domain.entites.EntityType
import com.mod.marveluniverse.domain.entites.RequestType
import database.RequestEntity

interface RequestDataSource {
    fun getRequest(
        type: RequestType,
        entity: EntityType,
        entityId: Int?,
        relatedEntity: String?,
        query: String?,
    ): RequestEntity

    suspend fun insertRequest(
        id: Int?,
        type: RequestType,
        entity: EntityType,
        entityId: Int?,
        relatedEntity: String?,
        query: String?,
        totalResults: Int,
        offset: Int?,
        etag: String,
        createdAt: Int,
        updatedAt: Int,
    )
}