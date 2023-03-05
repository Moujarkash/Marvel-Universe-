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
    fun getCharacters(): Flow<List<CharacterEntity>>
    fun getCharacterById(id: Int): CharacterEntity
    fun getCharactersByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<CharacterResourceEntity>>

    fun insertCharacters(
        characters: List<CharacterDto>
    )

    fun insertCharactersResource(
        resourceType: ResourceType,
        resourceId: Int,
        characters: List<CharacterDto>
    )

    fun clearCharacters()
    fun clearCharactersResource(resourceType: ResourceType, resourceId: Int)
}

class CharacterLocalDataSourceImpl(
    db: MarvelUniverseDatabase
) : CharacterLocalDataSource {
    private val characterQueries = db.characterQueries
    private val characterResourceQueries = db.character_resourceQueries

    override fun getCharacters(): Flow<List<CharacterEntity>> {
        return characterQueries
            .getCharacters()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override fun getCharacterById(id: Int): CharacterEntity {
        return characterQueries
            .getCharacterByRemoteId(id)
            .executeAsOne()
    }

    override fun getCharactersByResource(
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

    override fun insertCharacters(
        characters: List<CharacterDto>
    ) {
        characterQueries.transaction {
            characters.forEach {
                characterQueries
                    .insertCharacter(
                        id = null,
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

    override fun insertCharactersResource(
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

    override fun clearCharacters() {
        characterQueries.clearCharacters()
    }

    override fun clearCharactersResource(resourceType: ResourceType, resourceId: Int) {
        characterResourceQueries.clearCharactersResource(
            resourceType = resourceType,
            resourceId = resourceId
        )
    }
}