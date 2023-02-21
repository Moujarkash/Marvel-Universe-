package com.mod.marveluniverse.data.data_sources.remote

import com.mod.marveluniverse.data.ApiConstants
import com.mod.marveluniverse.data.dtos.CreatorDto
import com.mod.marveluniverse.data.dtos.ResponseWrapperDto
import com.mod.marveluniverse.domain.entites.ResourceType
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface CreatorRemoteDataSource {
    suspend fun fetchCreators(
        query: String?,
        limit: Int,
        offset: Int
    ): ResponseWrapperDto<List<CreatorDto>>

    suspend fun fetchCreatorsByResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int
    ): ResponseWrapperDto<List<CreatorDto>>
}

class CreatorRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : BaseRemoteDataSource(), CreatorRemoteDataSource {
    override suspend fun fetchCreators(
        query: String?,
        limit: Int,
        offset: Int
    ): ResponseWrapperDto<List<CreatorDto>> {
        return processRequest(
            request = {
                httpClient.get {
                    url(ApiConstants.BASE_URL + ApiConstants.API_V1 + "/creators")
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
        offset: Int
    ): ResponseWrapperDto<List<CreatorDto>> {
        return processRequest(
            request = {
                httpClient.get {
                    url(
                        ApiConstants.BASE_URL + ApiConstants.API_V1 + "/" + ApiConstants.getApiResourceUrlByType(
                            resourceType
                        ) + "/${resourceId}" + "/creators"
                    )
                }
            },
            onSuccess = { httpResponse ->
                httpResponse.body()
            }
        )
    }

}