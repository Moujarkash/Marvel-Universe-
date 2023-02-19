package com.mod.marveluniverse.domain.data_sources.local

import com.mod.marveluniverse.domain.entites.*
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import kotlinx.datetime.LocalDateTime

interface ComicLocalDataSource {
    fun getComics(): CommonFlow<List<Comic>>
    fun getComicById(id: Int): Comic
    fun getComicsByResource(resourceType: ResourceType, resourceId: Int): CommonFlow<List<Comic>>
    fun insertComic(
        id: Int,
        title: String,
        description: String,
        modified: LocalDateTime,
        isbn: String,
        pageCount: Int,
        textObjects: List<TextObject>,
        urls: List<Url>,
        series: SeriesSummary,
        variants: List<ComicSummary>,
        dates: List<ComicDate>,
        prices: List<ComicPrice>,
        thumbnail: Image,
        images: List<Image>,
        creators: CreatorsResourceList,
        characters: CharactersResourceList,
        stories: StoriesResourceList,
        events: EventsResourceList
    )
    fun insertComicResource(
        resourceType: ResourceType,
        resourceId: Int,
        id: Int,
        title: String,
        description: String,
        modified: LocalDateTime,
        isbn: String,
        pageCount: Int,
        textObjects: List<TextObject>,
        urls: List<Url>,
        series: SeriesSummary,
        variants: List<ComicSummary>,
        dates: List<ComicDate>,
        prices: List<ComicPrice>,
        thumbnail: Image,
        images: List<Image>,
        creators: CreatorsResourceList,
        characters: CharactersResourceList,
        stories: StoriesResourceList,
        events: EventsResourceList
    )
    fun clearComics()
    fun clearComicsResource(resourceType: ResourceType, resourceId: Int)
}