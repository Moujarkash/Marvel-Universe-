package com.mod.marveluniverse.data.data_sources.remote

import com.mod.marveluniverse.data.ApiConstants
import com.mod.marveluniverse.data.dtos.CharacterDto
import com.mod.marveluniverse.data.dtos.ResponseWrapperDto
import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.Sort
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface CharacterRemoteDataSource {
    suspend fun fetchCharacters(
        query: String?,
        limit: Int,
        offset: Int,
        sort: Sort,
        etag: String? = null
    ): ResponseWrapperDto<CharacterDto>

    suspend fun fetchCharactersByResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int,
        etag: String?= null
    ): ResponseWrapperDto<CharacterDto>
}

class CharacterRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : BaseRemoteDataSource(), CharacterRemoteDataSource {
    override suspend fun fetchCharacters(
        query: String?,
        limit: Int,
        offset: Int,
        sort: Sort,
        etag: String?
    ): ResponseWrapperDto<CharacterDto> {
        return processRequest(
            request = {
                httpClient.get {
                    url(ApiConstants.BASE_URL + ApiConstants.API_V1 + "/characters")
                    parameter("nameStartsWith", query)
                    parameter("limit", limit)
                    parameter("offset", offset)
                    parameter("orderBy", "${if (sort == Sort.DESCENDING) "-" else ""}name")
                    header("If-None-Match", etag)
                }
            },
            onSuccess = { httpResponse ->
                httpResponse.body()
            }
        )
    }

    override suspend fun fetchCharactersByResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int,
        etag: String?
    ): ResponseWrapperDto<CharacterDto> {
        return processRequest(
            request = {
                httpClient.get {
                    url(
                        ApiConstants.BASE_URL + ApiConstants.API_V1 + "/" + ApiConstants.getApiResourceUrlByType(
                            resourceType
                        ) + "/${resourceId}" + "/characters"
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