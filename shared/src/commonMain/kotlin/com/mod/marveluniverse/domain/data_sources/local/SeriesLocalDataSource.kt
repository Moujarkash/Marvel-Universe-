package com.mod.marveluniverse.domain.data_sources.local

import com.mod.marveluniverse.domain.entites.*
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import kotlinx.datetime.LocalDateTime

interface SeriesLocalDataSource {
    fun getSeries(): CommonFlow<List<Series>>
    fun getSeriesById(id: Int): Series
    fun getSeriesByResource(resourceType: ResourceType, resourceId: Int): CommonFlow<List<Series>>
    fun insertSeries(
        id: Int,
        title: String,
        description: String,
        startYear: Int,
        endYear: Int,
        rating: String,
        urls: List<Url>,
        modified: LocalDateTime,
        thumbnail: Image,
        comics: ComicsResourceList,
        stories: StoriesResourceList,
        events: EventsResourceList,
        characters: CharactersResourceList,
        creators: CreatorsResourceList,
        next: SeriesSummary?,
        previous: SeriesSummary?
    )
    fun insertSeriesResource(
        resourceType: ResourceType,
        resourceId: Int,
        id: Int,
        title: String,
        description: String,
        startYear: Int,
        endYear: Int,
        rating: String,
        urls: List<Url>,
        modified: LocalDateTime,
        thumbnail: Image,
        comics: ComicsResourceList,
        stories: StoriesResourceList,
        events: EventsResourceList,
        characters: CharactersResourceList,
        creators: CreatorsResourceList,
        next: SeriesSummary?,
        previous: SeriesSummary?
    )
    fun clearSeries()
    fun clearSeriesResource(resourceType: ResourceType, resourceId: Int)
}