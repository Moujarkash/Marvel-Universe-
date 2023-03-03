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

        val requestsNotMatchingQuery = requestLocalDataSource
            .getRequestsNotMatchingQuery(requestType, resourceType, query)

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
                    for (comicDto in response.data.results) {
                        comicLocalDataSource.insertComic(
                            id = comicDto.id,
                            title = comicDto.title,
                            description = comicDto.description,
                            modified = comicDto.modified,
                            isbn = comicDto.isbn,
                            pageCount = comicDto.pageCount,
                            textObjects = comicDto.textObjects,
                            urls = comicDto.urls,
                            series = comicDto.series,
                            variants = comicDto.variants,
                            dates = comicDto.dates,
                            prices = comicDto.prices,
                            thumbnail = comicDto.thumbnail,
                            images = comicDto.images,
                            creators = comicDto.creators,
                            characters = comicDto.characters,
                            stories = comicDto.stories,
                            events = comicDto.events
                        )
                    }

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
                    for (comicDto in response.data.results) {
                        comicLocalDataSource.insertComic(
                            id = comicDto.id,
                            title = comicDto.title,
                            description = comicDto.description,
                            modified = comicDto.modified,
                            isbn = comicDto.isbn,
                            pageCount = comicDto.pageCount,
                            textObjects = comicDto.textObjects,
                            urls = comicDto.urls,
                            series = comicDto.series,
                            variants = comicDto.variants,
                            dates = comicDto.dates,
                            prices = comicDto.prices,
                            thumbnail = comicDto.thumbnail,
                            images = comicDto.images,
                            creators = comicDto.creators,
                            characters = comicDto.characters,
                            stories = comicDto.stories,
                            events = comicDto.events
                        )
                    }
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

            for (comicDto in response.data.results) {
                comicLocalDataSource.insertComic(
                    id = comicDto.id,
                    title = comicDto.title,
                    description = comicDto.description,
                    modified = comicDto.modified,
                    isbn = comicDto.isbn,
                    pageCount = comicDto.pageCount,
                    textObjects = comicDto.textObjects,
                    urls = comicDto.urls,
                    series = comicDto.series,
                    variants = comicDto.variants,
                    dates = comicDto.dates,
                    prices = comicDto.prices,
                    thumbnail = comicDto.thumbnail,
                    images = comicDto.images,
                    creators = comicDto.creators,
                    characters = comicDto.characters,
                    stories = comicDto.stories,
                    events = comicDto.events
                )
            }
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

    override suspend fun requestComicsResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getComicsResource(
        resourceType: ResourceType,
        resourceId: Int
    ): CommonFlow<List<Comic>> = comicLocalDataSource.getComicsByResource(resourceType, resourceId)
        .map { comicResourceEntities ->
            comicResourceEntities.map { it.toDomainEntity() }
        }
        .toCommonFlow()
}