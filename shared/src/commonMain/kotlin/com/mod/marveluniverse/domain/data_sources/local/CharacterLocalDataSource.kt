package com.mod.marveluniverse.domain.data_sources.local

import com.mod.marveluniverse.domain.entites.*
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import kotlinx.datetime.LocalDateTime

interface CharacterLocalDataSource {
    fun getCharacters(): CommonFlow<List<Character>>
    fun getCharacterById(id: Int): Character
    fun getCharactersByResource(resourceType: ResourceType, resourceId: Int): CommonFlow<List<Character>>
    fun insertCharacter(
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
    )
    fun insertCharacterResource(
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
    )
    fun clearCharacters()
    fun clearCharactersResource(resourceType: ResourceType, resourceId: Int)
}