package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.dtos.*
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.entites.ResourceType
import database.series.SeriesEntity
import database.series.SeriesResourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

interface SeriesLocalDataSource {
    fun getSeries(): Flow<List<SeriesEntity>>
    fun getSeriesById(id: Int): SeriesEntity
    fun getSeriesByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<SeriesResourceEntity>>

    fun insertSeries(
        id: Int,
        title: String,
        description: String,
        startYear: Int,
        endYear: Int,
        rating: String,
        urls: List<UrlDto>,
        modified: LocalDateTime,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto,
        characters: CharactersResourceListDto,
        creators: CreatorsResourceListDto,
        next: SeriesSummaryDto?,
        previous: SeriesSummaryDto?
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
        urls: List<UrlDto>,
        modified: LocalDateTime,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto,
        characters: CharactersResourceListDto,
        creators: CreatorsResourceListDto,
        next: SeriesSummaryDto?,
        previous: SeriesSummaryDto?
    )

    fun clearSeries()
    fun clearSeriesResource(resourceType: ResourceType, resourceId: Int)
}

class SeriesLocalDataSourceImpl(
    db: MarvelUniverseDatabase
) : SeriesLocalDataSource {
    private val seriesQueries = db.seriesQueries
    private val seriesResourceQueries = db.series_resourceQueries

    override fun getSeries(): Flow<List<SeriesEntity>> {
        return seriesQueries
            .getSeries()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override fun getSeriesById(id: Int): SeriesEntity {
        return seriesQueries
            .getSeriesById(id)
            .executeAsOne()
    }

    override fun getSeriesByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<SeriesResourceEntity>> {
        return seriesResourceQueries
            .getSeriesResourceByType(resourceType, resourceId)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override fun insertSeries(
        id: Int,
        title: String,
        description: String,
        startYear: Int,
        endYear: Int,
        rating: String,
        urls: List<UrlDto>,
        modified: LocalDateTime,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto,
        characters: CharactersResourceListDto,
        creators: CreatorsResourceListDto,
        next: SeriesSummaryDto?,
        previous: SeriesSummaryDto?
    ) {
        seriesQueries
            .insertSeries(
                id = id,
                title = title,
                description = description,
                startYear = startYear,
                endYear = endYear,
                rating = rating,
                urls = urls,
                modified = modified,
                thumbnail = thumbnail,
                comics = comics,
                stories = stories,
                events = events,
                characters = characters,
                creators = creators,
                next = next,
                previous = previous
            )
    }

    override fun insertSeriesResource(
        resourceType: ResourceType,
        resourceId: Int,
        id: Int,
        title: String,
        description: String,
        startYear: Int,
        endYear: Int,
        rating: String,
        urls: List<UrlDto>,
        modified: LocalDateTime,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto,
        characters: CharactersResourceListDto,
        creators: CreatorsResourceListDto,
        next: SeriesSummaryDto?,
        previous: SeriesSummaryDto?
    ) {
        seriesResourceQueries
            .insertSeriesResource(
                resourceType = resourceType,
                resourceId = resourceId,
                id = id,
                title = title,
                description = description,
                startYear = startYear,
                endYear = endYear,
                rating = rating,
                urls = urls,
                modified = modified,
                thumbnail = thumbnail,
                comics = comics,
                stories = stories,
                events = events,
                characters = characters,
                creators = creators,
                next = next,
                previous = previous
            )
    }

    override fun clearSeries() {
        seriesQueries.clearSeries()
    }

    override fun clearSeriesResource(resourceType: ResourceType, resourceId: Int) {
        seriesResourceQueries.clearSeriesResource(
            resourceType = resourceType,
            resourceId = resourceId
        )
    }
}