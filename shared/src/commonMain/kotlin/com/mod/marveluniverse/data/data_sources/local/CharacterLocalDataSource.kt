package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.dtos.*
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.entites.ResourceType
import database.character.CharacterEntity
import database.character.CharacterResourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface CharacterLocalDataSource {
    suspend fun getCharacters(): Flow<List<CharacterEntity>>
    suspend fun getCharactersByRequestCode(requestCode: String): Flow<List<CharacterEntity>>
    suspend fun getCharacterById(id: Int): CharacterEntity
    suspend fun getCharactersByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<CharacterResourceEntity>>

    suspend fun insertCharacters(
        requestCode: String,
        characters: List<CharacterDto>
    )

    suspend fun insertCharactersResource(
        resourceType: ResourceType,
        resourceId: Int,
        characters: List<CharacterDto>
    )

    suspend fun clearCharactersByRequestCode(requestCode: String)
    suspend fun clearCharactersByRemoteIdsAndRequestCode(remoteIds: List<Int>, requestCode: String)
    suspend fun clearCharactersResource(resourceType: ResourceType, resourceId: Int)
}

class CharacterLocalDataSourceImpl(
    db: MarvelUniverseDatabase
) : CharacterLocalDataSource {
    private val characterQueries = db.characterQueries
    private val characterResourceQueries = db.character_resourceQueries

    override suspend fun getCharacters(): Flow<List<CharacterEntity>> {
        return characterQueries
            .getCharacters()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override suspend fun getCharactersByRequestCode(requestCode: String): Flow<List<CharacterEntity>> {
        return characterQueries
            .getCharactersByRequestCode(requestCode)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override suspend fun getCharacterById(id: Int): CharacterEntity {
        return characterQueries
            .getCharacterByRemoteId(id)
            .executeAsOne()
    }

    override suspend fun getCharactersByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<CharacterResourceEntity>> {
        return characterResourceQueries
            .getCharactersResourceByType(resourceType, resourceId)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override suspend fun insertCharacters(
        requestCode: String,
        characters: List<CharacterDto>
    ) {
        characterQueries.transaction {
            characters.forEach {
                characterQueries
                    .insertCharacter(
                        id = null,
                        requestCode = requestCode,
                        remoteId = it.id,
                        name = it.name,
                        description = it.description,
                        modified = it.modified,
                        urls = it.urls,
                        thumbnail = it.thumbnail,
                        comics = it.comics,
                        series = it.series,
                        stories = it.stories,
                        events = it.events
                    )
            }
        }


    }

    override suspend fun insertCharactersResource(
        resourceType: ResourceType,
        resourceId: Int,
        characters: List<CharacterDto>
    ) {
        characterResourceQueries.transaction {
            characters.forEach {
                characterResourceQueries
                    .insertCharacterResource(
                        id = null,
                        resourceType = resourceType,
                        resourceId = resourceId,
                        remoteId = it.id,
                        name = it.name,
                        description = it.description,
                        modified = it.modified,
                        urls = it.urls,
                        thumbnail = it.thumbnail,
                        comics = it.comics,
                        series = it.series,
                        stories = it.stories,
                        events = it.events
                    )
            }
        }
    }

    override suspend fun clearCharactersByRequestCode(requestCode: String) {
        characterQueries.clearRequestCharacters(requestCode)
    }

    override suspend fun clearCharactersByRemoteIdsAndRequestCode(
        remoteIds: List<Int>,
        requestCode: String
    ) {
        characterQueries.clearCharactersByRemoteIdsAndRequestCode(remoteIds, requestCode)
    }

    override suspend fun clearCharactersResource(resourceType: ResourceType, resourceId: Int) {
        characterResourceQueries.clearCharactersResource(
            resourceType = resourceType,
            resourceId = resourceId
        )
    }
}