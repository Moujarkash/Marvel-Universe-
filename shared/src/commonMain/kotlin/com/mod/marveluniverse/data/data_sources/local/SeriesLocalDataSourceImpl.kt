package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.mappers.toDataDto
import com.mod.marveluniverse.data.mappers.toDomainEntity
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.data_sources.local.SeriesLocalDataSource
import com.mod.marveluniverse.domain.entites.*
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import com.mod.marveluniverse.domain.utils.flows.toCommonFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

class SeriesLocalDataSourceImpl(
    db: MarvelUniverseDatabase
): SeriesLocalDataSource {
    private val seriesQueries = db.seriesQueries
    private val seriesResourceQueries = db.series_resourceQueries

    override fun getSeries(): CommonFlow<List<Series>> {
        return seriesQueries
            .getSeries()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
            .map { comics ->
                comics.map { it.toDomainEntity() }
            }
            .toCommonFlow()
    }

    override fun getSeriesById(id: Int): Series {
        return seriesQueries
            .getSeriesById(id)
            .executeAsOne()
            .toDomainEntity()
    }

    override fun getSeriesByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): CommonFlow<List<Series>> {
        return seriesResourceQueries
            .getSeriesResourceByType(resourceType, resourceId)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
            .map { comics ->
                comics.map { it.toDomainEntity() }
            }
            .toCommonFlow()
    }

    override fun insertSeries(
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
    ) {
        seriesQueries
            .insertSeries(
                id = id,
                title = title,
                description = description,
                startYear = startYear,
                endYear = endYear,
                rating = rating,
                urls = urls.map { it.toDataDto() },
                modified = modified,
                thumbnail = thumbnail.toDataDto(),
                comics = comics.toDataDto(),
                stories = stories.toDataDto(),
                events = events.toDataDto(),
                characters = characters.toDataDto(),
                creators = creators.toDataDto(),
                next = next?.toDataDto(),
                previous = previous?.toDataDto()
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
                urls = urls.map { it.toDataDto() },
                modified = modified,
                thumbnail = thumbnail.toDataDto(),
                comics = comics.toDataDto(),
                stories = stories.toDataDto(),
                events = events.toDataDto(),
                characters = characters.toDataDto(),
                creators = creators.toDataDto(),
                next = next?.toDataDto(),
                previous = previous?.toDataDto()
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