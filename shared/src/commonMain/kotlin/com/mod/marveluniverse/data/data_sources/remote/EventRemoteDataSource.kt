package com.mod.marveluniverse.data.data_sources.remote

import com.mod.marveluniverse.data.ApiConstants
import com.mod.marveluniverse.data.dtos.EventDto
import com.mod.marveluniverse.data.dtos.ResponseWrapperDto
import com.mod.marveluniverse.domain.entites.ResourceType
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface EventRemoteDataSource {
    suspend fun fetchEvents(
        query: String?,
        limit: Int,
        offset: Int,
        etag: String?
    ): ResponseWrapperDto<List<EventDto>>

    suspend fun fetchEventsByResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int,
        etag: String?
    ): ResponseWrapperDto<List<EventDto>>
}

class EventRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : BaseRemoteDataSource(), EventRemoteDataSource {
    override suspend fun fetchEvents(
        query: String?,
        limit: Int,
        offset: Int,
        etag: String?
    ): ResponseWrapperDto<List<EventDto>> {
        return processRequest(
            request = {
                httpClient.get {
                    url(ApiConstants.BASE_URL + ApiConstants.API_V1 + "/events")
                    etag?.let {
                        header("If-None-Match", it)
                    }
                }
            },
            onSuccess = { httpResponse ->
                httpResponse.body()
            }
        )
    }

    override suspend fun fetchEventsByResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int,
        etag: String?
    ): ResponseWrapperDto<List<EventDto>> {
        return processRequest(
            request = {
                httpClient.get {
                    url(
                        ApiConstants.BASE_URL + ApiConstants.API_V1 + "/" + ApiConstants.getApiResourceUrlByType(
                            resourceType
                        ) + "/${resourceId}" + "/events"
                    )
                    etag?.let {
                        header("If-None-Match", it)
                    }
                }
            },
            onSuccess = { httpResponse ->
                httpResponse.body()
            }
        )
    }

}