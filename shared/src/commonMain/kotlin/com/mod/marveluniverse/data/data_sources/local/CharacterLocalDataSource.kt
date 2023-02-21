package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.dtos.*
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.entites.ResourceType
import database.character.CharacterEntity
import database.character.CharacterResourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

interface CharacterLocalDataSource {
    fun getCharacters(): Flow<List<CharacterEntity>>
    fun getCharacterById(id: Int): CharacterEntity
    fun getCharactersByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<CharacterResourceEntity>>

    fun insertCharacter(
        id: Int,
        name: String,
        description: String,
        modified: LocalDateTime,
        urls: List<UrlDto>,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto
    )

    fun insertCharacterResource(
        resourceType: ResourceType,
        resourceId: Int,
        id: Int,
        name: String,
        description: String,
        modified: LocalDateTime,
        urls: List<UrlDto>,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto
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
            .getCharacterById(id)
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

    override fun insertCharacter(
        id: Int,
        name: String,
        description: String,
        modified: LocalDateTime,
        urls: List<UrlDto>,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto
    ) {
        characterQueries
            .insertCharacter(
                id = id,
                name = name,
                description = description,
                modified = modified,
                urls = urls,
                thumbnail = thumbnail,
                comics = comics,
                series = series,
                stories = stories,
                events = events
            )
    }

    override fun insertCharacterResource(
        resourceType: ResourceType,
        resourceId: Int,
        id: Int,
        name: String,
        description: String,
        modified: LocalDateTime,
        urls: List<UrlDto>,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto
    ) {
        characterResourceQueries
            .insertCharacterResource(
                resourceType = resourceType,
                resourceId = resourceId,
                id = id,
                name = name,
                description = description,
                modified = modified,
                urls = urls,
                thumbnail = thumbnail,
                comics = comics,
                series = series,
                stories = stories,
                events = events
            )
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