package com.mod.marveluniverse.domain.data_sources

import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.RequestType
import database.RequestEntity
import kotlinx.datetime.LocalDateTime

interface RequestDataSource {
    fun getRequest(
        type: RequestType,
        resourceType: ResourceType,
        resourceId: Int?,
        relatedEntity: String?,
        query: String?,
    ): RequestEntity

    suspend fun insertRequest(
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
        updatedAt: LocalDateTime,
    )
}