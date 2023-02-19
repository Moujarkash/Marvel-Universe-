package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.*
import com.mod.marveluniverse.data.mappers.toDataDto
import com.mod.marveluniverse.data.mappers.toDomainEntity
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.data_sources.local.ComicLocalDataSource
import com.mod.marveluniverse.domain.entites.*
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import com.mod.marveluniverse.domain.utils.flows.toCommonFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

class ComicLocalDataSourceImpl(
    db: MarvelUniverseDatabase
) : ComicLocalDataSource {
    private val comicQueries = db.comicQueries
    private val comicResourceQueries = db.comic_resourceQueries

    override fun getComics(): CommonFlow<List<Comic>> {
        return comicQueries
            .getComics()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
            .map { comics ->
                comics.map { it.toDomainEntity() }
            }
            .toCommonFlow()
    }


    override fun getComicById(id: Int): Comic {
        return comicQueries
            .getComicById(id)
            .executeAsOne()
            .toDomainEntity()
    }

    override fun getComicsByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): CommonFlow<List<Comic>> {
        return comicResourceQueries
            .getComicsResourceByType(resourceType, resourceId)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
            .map { comics ->
                comics.map { it.toDomainEntity() }
            }
            .toCommonFlow()
    }

    override fun insertComic(
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
    ) {
        comicQueries
            .insertComic(
                id = id,
                title = title,
                description = description,
                modified = modified,
                isbn = isbn,
                pageCount = pageCount,
                textObjects = textObjects.map { it.toDataDto() },
                urls = urls.map { it.toDataDto() },
                series = series.toDataDto(),
                variants = variants.map { it.toDataDto() },
                dates = dates.map { it.toDataDto() },
                prices = prices.map { it.toDataDto() },
                thumbnail = thumbnail.toDataDto(),
                images = images.map { it.toDataDto() },
                creators = creators.toDataDto(),
                characters = characters.toDataDto(),
                stories = stories.toDataDto(),
                events = events.toDataDto()
            )
    }

    override fun insertComicResource(
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
    ) {
        comicResourceQueries
            .insertComicResource(
                resourceType = resourceType,
                resourceId = resourceId,
                id = id,
                title = title,
                description = description,
                modified = modified,
                isbn = isbn,
                pageCount = pageCount,
                textObjects = textObjects.map { it.toDataDto() },
                urls = urls.map { it.toDataDto() },
                series = series.toDataDto(),
                variants = variants.map { it.toDataDto() },
                dates = dates.map { it.toDataDto() },
                prices = prices.map { it.toDataDto() },
                thumbnail = thumbnail.toDataDto(),
                images = images.map { it.toDataDto() },
                creators = creators.toDataDto(),
                characters = characters.toDataDto(),
                stories = stories.toDataDto(),
                events = events.toDataDto()
            )
    }

    override fun clearComics() {
        comicQueries.clearComics()
    }

    override fun clearComicsResource(resourceType: ResourceType, resourceId: Int) {
       comicResourceQueries.clearComicsResource(
           resourceType = resourceType,
           resourceId = resourceId
       )
    }
}