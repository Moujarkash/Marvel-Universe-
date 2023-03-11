package com.mod.marveluniverse.data.repositories

import com.mod.marveluniverse.data.data_sources.local.CharacterLocalDataSource
import com.mod.marveluniverse.data.data_sources.local.RequestLocalDataSource
import com.mod.marveluniverse.data.data_sources.remote.CharacterRemoteDataSource
import com.mod.marveluniverse.data.extensions.isExpired
import com.mod.marveluniverse.data.mappers.toDomainEntity
import com.mod.marveluniverse.domain.entites.*
import com.mod.marveluniverse.domain.error.AppException
import com.mod.marveluniverse.domain.repositories.CharacterRepository
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import com.mod.marveluniverse.domain.utils.flows.toCommonFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class CharacterRepositoryImpl(
    private val requestLocalDataSource: RequestLocalDataSource,
    private val characterLocalDataSource: CharacterLocalDataSource,
    private val characterRemoteDataSource: CharacterRemoteDataSource
): CharacterRepository {
    override suspend fun requestCharacters(query: String?, sort: Sort, limit: Int, offset: Int) {
        val requestType = RequestType.LIST
        val resourceType = ResourceType.CHARACTER
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

                        val response = characterRemoteDataSource.fetchCharacters(
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
                            characterLocalDataSource.clearCharactersByRequestCode(requestCode)
                        } else {
                            requestLocalDataSource.refreshRequest(request)
                            characterLocalDataSource.clearCharactersByRemoteIdsAndRequestCode(response.data.results.map { it.id }, requestCode)
                        }

                        characterLocalDataSource.insertCharacters(code, response.data.results)

                    } catch (e: AppException.DataNotChangedOnServer) {
                        requestLocalDataSource.refreshRequest(request)
                    }
                }
            } else {
                val response = characterRemoteDataSource.fetchCharacters(
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

                characterLocalDataSource.insertCharacters(requestCode, response.data.results)
            }
        } else {
            val response = characterRemoteDataSource.fetchCharacters(
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
            characterLocalDataSource.insertCharacters(generatedCode, response.data.results)
        }
    }

    override suspend fun getCharacters(query: String?, sort: Sort): CommonFlow<List<Character>> {
        val requestCode = requestLocalDataSource.getRequestCode(
            type = RequestType.LIST,
            resourceType = ResourceType.CHARACTER,
            query = query,
            sort = sort
        )
        return if (requestCode != null) {
            characterLocalDataSource.getCharactersByRequestCode(requestCode)
                .map { characterEntities ->
                    characterEntities.map { it.toDomainEntity() }
                }
                .toCommonFlow()
        } else {
            flow<List<Character>> {
                emit(emptyList())
            }.toCommonFlow()
        }
    }
}