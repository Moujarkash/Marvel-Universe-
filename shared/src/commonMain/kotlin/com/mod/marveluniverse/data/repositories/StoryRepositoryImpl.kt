package com.mod.marveluniverse.data.repositories

import com.mod.marveluniverse.data.data_sources.local.StoryLocalDataSource
import com.mod.marveluniverse.data.data_sources.local.RequestLocalDataSource
import com.mod.marveluniverse.data.data_sources.remote.StoryRemoteDataSource
import com.mod.marveluniverse.data.extensions.isExpired
import com.mod.marveluniverse.data.mappers.toDomainEntity
import com.mod.marveluniverse.domain.entites.Story
import com.mod.marveluniverse.domain.entites.RequestType
import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.error.AppException
import com.mod.marveluniverse.domain.repositories.StoryRepository
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import com.mod.marveluniverse.domain.utils.flows.toCommonFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class StoryRepositoryImpl(
    private val requestLocalDataSource: RequestLocalDataSource,
    private val storyLocalDataSource: StoryLocalDataSource,
    private val storyRemoteDataSource: StoryRemoteDataSource
): StoryRepository {
    override suspend fun requestStories(query: String?, sort: Sort, limit: Int, offset: Int) {
        val requestType = RequestType.LIST
        val resourceType = ResourceType.STORY
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        val requestCode = requestLocalDataSource.getRequestCode(
            type = requestType,
            resourceType = resourceType,
            query = query,
            sort = sort
        )
        if (requestCode != null) {
            val request = requestLocalDataSource
                .getRequest(
                    type = requestType,
                    resourceType = resourceType,
                    query = query,
                    sort = sort,
                    offset = offset,
                    limit = limit
                )
            if (request != null) {
                if (request.isExpired()) {
                    try {
                        var offsetValue = offset
                        var etagValue: String? = request.etag
                        var shouldResetData = false
                        if (offset == request.totalResults) {
                            offsetValue = 0
                            etagValue = null
                            shouldResetData = true
                        }

                        val response = storyRemoteDataSource.fetchStories(
                            query = query,
                            limit = limit,
                            offset = offsetValue,
                            sort = sort,
                            etag = etagValue
                        )

                        var code = requestCode
                        if (shouldResetData) {
                            requestLocalDataSource.deleteRequestsByCode(code)
                            code = requestLocalDataSource.insertRequest(
                                type = requestType,
                                resourceType = resourceType,
                                query = query,
                                sort = sort,
                                totalResults = response.data.total,
                                limit = limit,
                                offset = offsetValue,
                                etag = response.etag,
                                createdAt = now,
                                updatedAt = now
                            )
                            storyLocalDataSource.clearStoriesByRequestCode(requestCode)
                        } else {
                            requestLocalDataSource.refreshRequest(request)
                            storyLocalDataSource.clearStoriesByRemoteIdsAndRequestCode(response.data.results.map { it.id }, requestCode)
                        }

                        storyLocalDataSource.insertStories(code, response.data.results)

                    } catch (e: AppException.DataNotChangedOnServer) {
                        requestLocalDataSource.refreshRequest(request)
                    }
                }
            } else {
                val response = storyRemoteDataSource.fetchStories(
                    query = query,
                    limit = limit,
                    sort = sort,
                    offset = offset
                )

                requestLocalDataSource.insertRequest(
                    type = requestType,
                    code = requestCode,
                    resourceType = resourceType,
                    query = query,
                    sort = sort,
                    totalResults = response.data.total,
                    limit = limit,
                    offset = offset,
                    etag = response.etag,
                    createdAt = now,
                    updatedAt = now
                )

                storyLocalDataSource.insertStories(requestCode, response.data.results)
            }
        } else {
            val response = storyRemoteDataSource.fetchStories(
                query = query,
                limit = limit,
                sort = sort,
                offset = offset
            )

            val generatedCode = requestLocalDataSource.insertRequest(
                type = requestType,
                resourceType = resourceType,
                query = query,
                sort = sort,
                totalResults = response.data.total,
                limit = limit,
                offset = offset,
                etag = response.etag,
                createdAt = now,
                updatedAt = now
            )
            storyLocalDataSource.insertStories(generatedCode, response.data.results)
        }
    }

    override suspend fun getStories(query: String?, sort: Sort): CommonFlow<List<Story>> {
        val requestCode = requestLocalDataSource.getRequestCode(
            type = RequestType.LIST,
            resourceType = ResourceType.STORY,
            query = query,
            sort = sort
        )
        return if (requestCode != null) {
            storyLocalDataSource.getStoriesByRequestCode(requestCode)
                .map { storyEntities ->
                    storyEntities.map { it.toDomainEntity() }
                }
                .toCommonFlow()
        } else {
            flow<List<Story>> {
                emit(emptyList())
            }.toCommonFlow()
        }
    }
}