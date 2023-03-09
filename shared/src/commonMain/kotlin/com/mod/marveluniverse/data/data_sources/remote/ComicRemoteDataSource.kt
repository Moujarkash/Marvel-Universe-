package com.mod.marveluniverse.data.data_sources.remote

import com.mod.marveluniverse.data.ApiConstants
import com.mod.marveluniverse.data.dtos.ComicDto
import com.mod.marveluniverse.data.dtos.ResponseWrapperDto
import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.Sort
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface ComicRemoteDataSource {
    suspend fun fetchComics(
        query: String?,
        limit: Int,
        offset: Int,
        sort: Sort,
        etag: String? = null
    ): ResponseWrapperDto<ComicDto>

    suspend fun fetchComicsByResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int,
        etag: String? = null
    ): ResponseWrapperDto<ComicDto>
}

class ComicRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : BaseRemoteDataSource(), ComicRemoteDataSource {
    override suspend fun fetchComics(
        query: String?,
        limit: Int,
        offset: Int,
        sort: Sort,
        etag: String?
    ): ResponseWrapperDto<ComicDto> {
        return processRequest(
            request = {
                httpClient.get {
                    url(ApiConstants.BASE_URL + ApiConstants.API_V1 + "/comics")
                    parameter("titleStartsWith", query)
                    parameter("limit", limit)
                    parameter("offset", offset)
                    parameter("orderBy", "${if (sort == Sort.DESCENDING) "-" else ""}title")
                    header("If-None-Match", etag)
                }
            },
            onSuccess = { httpResponse ->
                httpResponse.body()
            }
        )
    }

    override suspend fun fetchComicsByResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int,
        etag: String?
    ): ResponseWrapperDto<ComicDto> {
        return processRequest(
            request = {
                httpClient.get {
                    url(
                        ApiConstants.BASE_URL + ApiConstants.API_V1 + "/" + ApiConstants.getApiResourceUrlByType(
                            resourceType
                        ) + "/${resourceId}" + "/comics"
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