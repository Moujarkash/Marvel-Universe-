package com.mod.marveluniverse.data.data_sources.remote

import com.mod.marveluniverse.data.ApiConstants
import com.mod.marveluniverse.data.dtos.ComicDto
import com.mod.marveluniverse.data.dtos.ResponseWrapperDto
import com.mod.marveluniverse.domain.entites.ResourceType
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface ComicRemoteDataSource {
    suspend fun fetchComics(
        query: String?,
        limit: Int,
        offset: Int,
        etag: String?
    ): ResponseWrapperDto<List<ComicDto>>

    suspend fun fetchComicsByResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int,
        etag: String?
    ): ResponseWrapperDto<List<ComicDto>>
}

class ComicRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : BaseRemoteDataSource(), ComicRemoteDataSource {
    override suspend fun fetchComics(
        query: String?,
        limit: Int,
        offset: Int,
        etag: String?
    ): ResponseWrapperDto<List<ComicDto>> {
        return processRequest(
            request = {
                httpClient.get {
                    url(ApiConstants.BASE_URL + ApiConstants.API_V1 + "/comics")
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

    override suspend fun fetchComicsByResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int,
        etag: String?
    ): ResponseWrapperDto<List<ComicDto>> {
        return processRequest(
            request = {
                httpClient.get {
                    url(
                        ApiConstants.BASE_URL + ApiConstants.API_V1 + "/" + ApiConstants.getApiResourceUrlByType(
                            resourceType
                        ) + "/${resourceId}" + "/comics"
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