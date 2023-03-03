package com.mod.marveluniverse.data.data_sources.remote

import com.mod.marveluniverse.data.ApiConstants
import com.mod.marveluniverse.data.dtos.ResponseWrapperDto
import com.mod.marveluniverse.data.dtos.StoryDto
import com.mod.marveluniverse.domain.entites.ResourceType
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface StoryRemoteDataSource {
    suspend fun fetchStories(
        query: String?,
        limit: Int,
        offset: Int,
        etag: String? = null
    ): ResponseWrapperDto<StoryDto>

    suspend fun fetchStoriesByResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int,
        etag: String? = null
    ): ResponseWrapperDto<StoryDto>
}

class StoryRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : BaseRemoteDataSource(), StoryRemoteDataSource {
    override suspend fun fetchStories(
        query: String?,
        limit: Int,
        offset: Int,
        etag: String?
    ): ResponseWrapperDto<StoryDto> {
        return processRequest(
            request = {
                httpClient.get {
                    url(ApiConstants.BASE_URL + ApiConstants.API_V1 + "/stories")
                    parameter("limit", limit)
                    parameter("offset", offset)
                    header("If-None-Match", etag)
                }
            },
            onSuccess = { httpResponse ->
                httpResponse.body()
            }
        )
    }

    override suspend fun fetchStoriesByResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int,
        etag: String?
    ): ResponseWrapperDto<StoryDto> {
        return processRequest(
            request = {
                httpClient.get {
                    url(
                        ApiConstants.BASE_URL + ApiConstants.API_V1 + "/" + ApiConstants.getApiResourceUrlByType(
                            resourceType
                        ) + "/${resourceId}" + "/stories"
                    )
                    parameter("limit", limit)
                    parameter("offset", offset)
                    header("If-None-Match", etag)
                }
            },
            onSuccess = { httpResponse ->
                httpResponse.body()
            }
        )
    }

}