package com.mod.marveluniverse.data.data_sources.remote

import com.mod.marveluniverse.data.ApiConstants
import com.mod.marveluniverse.data.dtos.ResponseWrapperDto
import com.mod.marveluniverse.data.dtos.SeriesDto
import com.mod.marveluniverse.domain.entites.ResourceType
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface SeriesRemoteDataSource {
    suspend fun fetchSeries(
        query: String?,
        limit: Int,
        offset: Int
    ): ResponseWrapperDto<List<SeriesDto>>

    suspend fun fetchSeriesByResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int
    ): ResponseWrapperDto<List<SeriesDto>>
}

class SeriesRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : BaseRemoteDataSource(), SeriesRemoteDataSource {
    override suspend fun fetchSeries(
        query: String?,
        limit: Int,
        offset: Int
    ): ResponseWrapperDto<List<SeriesDto>> {
        return processRequest(
            request = {
                httpClient.get {
                    url(ApiConstants.BASE_URL + ApiConstants.API_V1 + "/series")
                }
            },
            onSuccess = { httpResponse ->
                httpResponse.body()
            }
        )
    }

    override suspend fun fetchSeriesByResource(
        resourceType: ResourceType,
        resourceId: Int,
        limit: Int,
        offset: Int
    ): ResponseWrapperDto<List<SeriesDto>> {
        return processRequest(
            request = {
                httpClient.get {
                    url(
                        ApiConstants.BASE_URL + ApiConstants.API_V1 + "/" + ApiConstants.getApiResourceUrlByType(
                            resourceType
                        ) + "/${resourceId}" + "/series"
                    )
                }
            },
            onSuccess = { httpResponse ->
                httpResponse.body()
            }
        )
    }

}