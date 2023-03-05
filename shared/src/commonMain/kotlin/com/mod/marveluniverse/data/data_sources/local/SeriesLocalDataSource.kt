package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.dtos.*
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.entites.ResourceType
import database.series.SeriesEntity
import database.series.SeriesResourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SeriesLocalDataSource {
    fun getSeries(): Flow<List<SeriesEntity>>
    fun getSeriesById(id: Int): SeriesEntity
    fun getSeriesByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<SeriesResourceEntity>>

    fun insertSeries(
        series: List<SeriesDto>
    )

    fun insertSeriesResource(
        resourceType: ResourceType,
        resourceId: Int,
        series: List<SeriesDto>
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
            .getSeriesByRemoteId(id)
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
        series: List<SeriesDto>
    ) {
        seriesQueries.transaction {
            series.forEach {
                seriesQueries
                    .insertSeries(
                        id = null,
                        remoteId = it.id,
                        title = it.title,
                        description = it.description,
                        startYear = it.startYear,
                        endYear = it.endYear,
                        rating = it.rating,
                        urls = it.urls,
                        modified = it.modified,
                        thumbnail = it.thumbnail,
                        comics = it.comics,
                        stories = it.stories,
                        events = it.events,
                        characters = it.characters,
                        creators = it.creators,
                        next = it.next,
                        previous = it.previous
                    )
            }
        }
    }

    override fun insertSeriesResource(
        resourceType: ResourceType,
        resourceId: Int,
        series: List<SeriesDto>
    ) {
        seriesResourceQueries.transaction {
            series.forEach {
                seriesResourceQueries
                    .insertSeriesResource(
                        id = null,
                        resourceType = resourceType,
                        resourceId = resourceId,
                        remoteId = it.id,
                        title = it.title,
                        description = it.description,
                        startYear = it.startYear,
                        endYear = it.endYear,
                        rating = it.rating,
                        urls = it.urls,
                        modified = it.modified,
                        thumbnail = it.thumbnail,
                        comics = it.comics,
                        stories = it.stories,
                        events = it.events,
                        characters = it.characters,
                        creators = it.creators,
                        next = it.next,
                        previous = it.previous
                    )
            }
        }
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