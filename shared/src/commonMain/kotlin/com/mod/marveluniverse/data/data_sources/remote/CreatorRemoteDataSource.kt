package com.mod.marveluniverse.data.data_sources.remote

import com.mod.marveluniverse.data.ApiConstants
import com.mod.marveluniverse.data.dtos.CreatorDto
import com.mod.marveluniverse.data.dtos.ResponseWrapperDto
import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.Sort
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface CreatorRemoteDataSource {
    suspend fun fetchCreators(
        query: String?,
        limit: Int,
        offset: Int,
        sort: Sort,
        etag: String? = null
    ): ResponseWrapperDto<CreatorDto>

    suspend fun fetchCreatorsByResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int,
        etag: String? = null
    ): ResponseWrapperDto<CreatorDto>
}

class CreatorRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : BaseRemoteDataSource(), CreatorRemoteDataSource {
    override suspend fun fetchCreators(
        query: String?,
        limit: Int,
        offset: Int,
        sort: Sort,
        etag: String?
    ): ResponseWrapperDto<CreatorDto> {
        return processRequest(
            request = {
                httpClient.get {
                    url(ApiConstants.BASE_URL + ApiConstants.API_V1 + "/creators")
                    parameter("nameStartsWith", query)
                    parameter("limit", limit)
                    parameter("offset", offset)
                    parameter("orderBy", "${if (sort == Sort.DESCENDING) "-" else ""}firstName")
                    header("If-None-Match", etag)
                }
            },
            onSuccess = { httpResponse ->
                httpResponse.body()
            }
        )
    }

    override suspend fun fetchCreatorsByResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int,
        etag: String?
    ): ResponseWrapperDto<CreatorDto> {
        return processRequest(
            request = {
                httpClient.get {
                    url(
                        ApiConstants.BASE_URL + ApiConstants.API_V1 + "/" + ApiConstants.getApiResourceUrlByType(
                            resourceType
                        ) + "/${resourceId}" + "/creators"
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