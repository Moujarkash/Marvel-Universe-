package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.mappers.toDataDto
import com.mod.marveluniverse.data.mappers.toDomainEntity
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.data_sources.local.CharacterLocalDataSource
import com.mod.marveluniverse.domain.entites.*
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import com.mod.marveluniverse.domain.utils.flows.toCommonFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

class CharacterLocalDataSourceImpl(
    db: MarvelUniverseDatabase
): CharacterLocalDataSource {
    private val characterQueries = db.characterQueries
    private val characterResourceQueries = db.character_resourceQueries

    override fun getCharacters(): CommonFlow<List<Character>> {
        return characterQueries
            .getCharacters()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
            .map { comics ->
                comics.map { it.toDomainEntity() }
            }
            .toCommonFlow()
    }

    override fun getCharacterById(id: Int): Character {
        return characterQueries
            .getCharacterById(id)
            .executeAsOne()
            .toDomainEntity()
    }

    override fun getCharactersByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): CommonFlow<List<Character>> {
        return characterResourceQueries
            .getCharactersResourceByType(resourceType, resourceId)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
            .map { comics ->
                comics.map { it.toDomainEntity() }
            }
            .toCommonFlow()
    }

    override fun insertCharacter(
        id: Int,
        name: String,
        description: String,
        modified: LocalDateTime,
        urls: List<Url>,
        thumbnail: Image,
        comics: ComicsResourceList,
        series: SeriesResourceList,
        stories: StoriesResourceList,
        events: EventsResourceList
    ) {
        characterQueries
            .insertCharacter(
                id = id,
                name = name,
                description = description,
                modified = modified,
                urls = urls.map { it.toDataDto() },
                thumbnail = thumbnail.toDataDto(),
                comics = comics.toDataDto(),
                series = series.toDataDto(),
                stories = stories.toDataDto(),
                events = events.toDataDto()
            )
    }

    override fun insertCharacterResource(
        resourceType: ResourceType,
        resourceId: Int,
        id: Int,
        name: String,
        description: String,
        modified: LocalDateTime,
        urls: List<Url>,
        thumbnail: Image,
        comics: ComicsResourceList,
        series: SeriesResourceList,
        stories: StoriesResourceList,
        events: EventsResourceList
    ) {
        characterResourceQueries
            .insertCharacterResource(
                resourceType = resourceType,
                resourceId = resourceId,
                id = id,
                name = name,
                description = description,
                modified = modified,
                urls = urls.map { it.toDataDto() },
                thumbnail = thumbnail.toDataDto(),
                comics = comics.toDataDto(),
                series = series.toDataDto(),
                stories = stories.toDataDto(),
                events = events.toDataDto()
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