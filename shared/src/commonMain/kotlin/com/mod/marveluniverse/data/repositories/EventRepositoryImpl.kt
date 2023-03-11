package com.mod.marveluniverse.data.repositories

import com.mod.marveluniverse.data.data_sources.local.EventLocalDataSource
import com.mod.marveluniverse.data.data_sources.local.RequestLocalDataSource
import com.mod.marveluniverse.data.data_sources.remote.EventRemoteDataSource
import com.mod.marveluniverse.data.extensions.isExpired
import com.mod.marveluniverse.data.mappers.toDomainEntity
import com.mod.marveluniverse.domain.entites.Event
import com.mod.marveluniverse.domain.entites.RequestType
import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.error.AppException
import com.mod.marveluniverse.domain.repositories.EventRepository
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import com.mod.marveluniverse.domain.utils.flows.toCommonFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class EventRepositoryImpl(
    private val requestLocalDataSource: RequestLocalDataSource,
    private val eventLocalDataSource: EventLocalDataSource,
    private val eventRemoteDataSource: EventRemoteDataSource
): EventRepository {
    override suspend fun requestEvents(query: String?, sort: Sort, limit: Int, offset: Int) {
        val requestType = RequestType.LIST
        val resourceType = ResourceType.EVENT
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

                        val response = eventRemoteDataSource.fetchEvents(
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
                            eventLocalDataSource.clearEventsByRequestCode(requestCode)
                        } else {
                            requestLocalDataSource.refreshRequest(request)
                            eventLocalDataSource.clearEventsByRemoteIdsAndRequestCode(response.data.results.map { it.id }, requestCode)
                        }

                        eventLocalDataSource.insertEvents(code, response.data.results)

                    } catch (e: AppException.DataNotChangedOnServer) {
                        requestLocalDataSource.refreshRequest(request)
                    }
                }
            } else {
                val response = eventRemoteDataSource.fetchEvents(
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

                eventLocalDataSource.insertEvents(requestCode, response.data.results)
            }
        } else {
            val response = eventRemoteDataSource.fetchEvents(
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
            eventLocalDataSource.insertEvents(generatedCode, response.data.results)
        }
    }

    override suspend fun getEvents(query: String?, sort: Sort): CommonFlow<List<Event>> {
        val requestCode = requestLocalDataSource.getRequestCode(
            type = RequestType.LIST,
            resourceType = ResourceType.EVENT,
            query = query,
            sort = sort
        )
        return if (requestCode != null) {
            eventLocalDataSource.getEventsByRequestCode(requestCode)
                .map { eventEntities ->
                    eventEntities.map { it.toDomainEntity() }
                }
                .toCommonFlow()
        } else {
            flow<List<Event>> {
                emit(emptyList())
            }.toCommonFlow()
        }
    }
}