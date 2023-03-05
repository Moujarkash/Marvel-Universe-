package com.mod.marveluniverse.data.repositories

import com.mod.marveluniverse.data.data_sources.local.ComicLocalDataSource
import com.mod.marveluniverse.data.data_sources.local.RequestLocalDataSource
import com.mod.marveluniverse.data.data_sources.remote.ComicRemoteDataSource
import com.mod.marveluniverse.data.extensions.isExpired
import com.mod.marveluniverse.data.mappers.toDomainEntity
import com.mod.marveluniverse.domain.entites.Comic
import com.mod.marveluniverse.domain.entites.RequestType
import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.error.AppException
import com.mod.marveluniverse.domain.repositories.ComicRepository
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import com.mod.marveluniverse.domain.utils.flows.toCommonFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ComicRepositoryImpl(
    private val requestLocalDataSource: RequestLocalDataSource,
    private val comicLocalDataSource: ComicLocalDataSource,
    private val comicRemoteDataSource: ComicRemoteDataSource
) : ComicRepository {
    override suspend fun requestComics(query: String?, limit: Int, offset: Int) {
        val requestType = RequestType.LIST
        val resourceType = ResourceType.COMIC
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        val request = requestLocalDataSource
            .getRequest(
                type = requestType,
                resourceType = resourceType,
                query = query,
                offset = offset,
                limit = limit
            )
        if (request != null) {

            if (request.isExpired()) {
                try {
                    var offsetValue = offset
                    var etagValue: String? = request.etag
                    if (offset == request.totalResults) {
                        offsetValue = 0
                        etagValue = null
                    }

                    val response = comicRemoteDataSource.fetchComics(
                        query = query,
                        limit = limit,
                        offset = offsetValue,
                        etag = etagValue
                    )

                    requestLocalDataSource.deleteRequestsByTypeAndResource(requestType, resourceType)
                    requestLocalDataSource.insertRequest(
                        type = requestType,
                        resourceType = resourceType,
                        query = query,
                        totalResults = response.data.total,
                        limit = limit,
                        offset = offsetValue,
                        etag = response.etag,
                        createdAt = now,
                        updatedAt = now
                    )

                    comicLocalDataSource.clearComics()
                    comicLocalDataSource.insertComics(response.data.results)

                } catch (e: AppException.DataNotChangedOnServer) {
                    requestLocalDataSource.refreshRequest(request)
                }
            } else {
                if (offset == request.totalResults) return

                try {
                    val response = comicRemoteDataSource.fetchComics(
                        query = query,
                        limit = limit,
                        offset = offset,
                        etag = request.etag
                    )

                    requestLocalDataSource.deleteRequestsByTypeAndResource(requestType, resourceType)
                    requestLocalDataSource.insertRequest(
                        type = requestType,
                        resourceType = resourceType,
                        query = query,
                        totalResults = response.data.total,
                        limit = limit,
                        offset = offset,
                        etag = response.etag,
                        createdAt = now,
                        updatedAt = now
                    )

                    comicLocalDataSource.clearComics()
                    comicLocalDataSource.insertComics(response.data.results)
                } catch (e: AppException.DataNotChangedOnServer) {
                    requestLocalDataSource.refreshRequest(request)
                }
            }
        } else {
            val response = comicRemoteDataSource.fetchComics(
                query = query,
                limit = limit,
                offset = offset
            )

            val requestsWithDifferentQuery = requestLocalDataSource
                .getRequestsNotMatchingQuery(requestType, resourceType, query)
            if (requestsWithDifferentQuery.isNotEmpty()) {
                requestLocalDataSource.deleteRequestsByTypeAndResource(requestType, resourceType)
                comicLocalDataSource.clearComics()
            }

            requestLocalDataSource.insertRequest(
                type = requestType,
                resourceType = resourceType,
                query = query,
                totalResults = response.data.total,
                limit = limit,
                offset = offset,
                etag = response.etag,
                createdAt = now,
                updatedAt = now
            )

            comicLocalDataSource.insertComics(response.data.results)
        }
    }

    override fun getComics(): CommonFlow<List<Comic>> = comicLocalDataSource.getComics()
        .map { comicEntities ->
            comicEntities.map { it.toDomainEntity() }
        }
        .toCommonFlow()

    override fun getComicById(id: Int): Comic {
        val comicEntity = comicLocalDataSource.getComicById(id)
            ?: throw AppException.DataNotFound

        return comicEntity.toDomainEntity()
    }

    override suspend fun requestComicsForEntity(
        relatedEntity: ResourceType,
        relatedEntityId: Int,
        limit: Int,
        offset: Int
    ) {
        val requestType = RequestType.RELATED_ENTITY
        val resourceType = ResourceType.COMIC
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        val request = requestLocalDataSource
            .getRequest(
                type = requestType,
                resourceType = resourceType,
                relatedEntity = relatedEntity,
                relatedEntityId = relatedEntityId,
                offset = offset,
                limit = limit
            )
        if (request != null) {
            if (request.isExpired()) {
                try {
                    var offsetValue = offset
                    var etagValue: String? = request.etag
                    if (offset == request.totalResults) {
                        offsetValue = 0
                        etagValue = null
                    }

                    val response = comicRemoteDataSource.fetchComicsByResource(
                        resourceType = relatedEntity,
                        resourceId = relatedEntityId,
                        limit = limit,
                        offset = offsetValue,
                        etag = etagValue
                    )

                    requestLocalDataSource.deleteRequestsByTypeAndResource(requestType, resourceType)
                    requestLocalDataSource.insertRequest(
                        type = requestType,
                        resourceType = resourceType,
                        relatedEntity = relatedEntity,
                        relatedEntityId = relatedEntityId,
                        totalResults = response.data.total,
                        limit = limit,
                        offset = offsetValue,
                        etag = response.etag,
                        createdAt = now,
                        updatedAt = now
                    )

                    comicLocalDataSource.clearComicsResource(relatedEntity, relatedEntityId)
                    comicLocalDataSource.insertComicsResource(
                        resourceType = relatedEntity,
                        resourceId = relatedEntityId,
                        comics = response.data.results
                    )

                } catch (e: AppException.DataNotChangedOnServer) {
                    requestLocalDataSource.refreshRequest(request)
                }
            } else {
                if (offset == request.totalResults) return

                try {
                    val response = comicRemoteDataSource.fetchComicsByResource(
                        resourceType = relatedEntity,
                        resourceId = relatedEntityId,
                        limit = limit,
                        offset = offset,
                        etag = request.etag
                    )

                    requestLocalDataSource.deleteRequestsByTypeAndResource(requestType, resourceType)
                    requestLocalDataSource.insertRequest(
                        type = requestType,
                        resourceType = resourceType,
                        relatedEntity = relatedEntity,
                        relatedEntityId = relatedEntityId,
                        totalResults = response.data.total,
                        limit = limit,
                        offset = offset,
                        etag = response.etag,
                        createdAt = now,
                        updatedAt = now
                    )

                    comicLocalDataSource.clearComicsResource(relatedEntity, relatedEntityId)
                    comicLocalDataSource.insertComicsResource(
                        resourceType = relatedEntity,
                        resourceId = relatedEntityId,
                        comics = response.data.results
                    )
                } catch (e: AppException.DataNotChangedOnServer) {
                    requestLocalDataSource.refreshRequest(request)
                }
            }
        } else {
            val response = comicRemoteDataSource.fetchComicsByResource(
                resourceType = relatedEntity,
                resourceId = relatedEntityId,
                limit = limit,
                offset = offset
            )

            requestLocalDataSource.insertRequest(
                type = requestType,
                resourceType = resourceType,
                relatedEntity = relatedEntity,
                relatedEntityId = relatedEntityId,
                totalResults = response.data.total,
                limit = limit,
                offset = offset,
                etag = response.etag,
                createdAt = now,
                updatedAt = now
            )

            comicLocalDataSource.insertComicsResource(
                resourceType = relatedEntity,
                resourceId = relatedEntityId,
                comics = response.data.results
            )
        }
    }

    override fun getComicsEntity(
        relatedEntity: ResourceType,
        relatedEntityId: Int
    ): CommonFlow<List<Comic>> = comicLocalDataSource.getComicsByResource(
        relatedEntity,
        relatedEntityId
    )
        .map { comicResourceEntities ->
            comicResourceEntities.map { it.toDomainEntity() }
        }
        .toCommonFlow()
}