package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.dtos.*
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.entites.ResourceType
import database.comic.ComicEntity
import database.comic.ComicResourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

interface ComicLocalDataSource {
    fun getComics(): Flow<List<ComicEntity>>
    fun getComicById(id: Int): ComicEntity?
    fun getComicsByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<ComicResourceEntity>>

    fun insertComic(
        id: Int,
        title: String,
        description: String,
        modified: LocalDateTime,
        isbn: String,
        pageCount: Int,
        textObjects: List<TextObjectDto>,
        urls: List<UrlDto>,
        series: SeriesSummaryDto,
        variants: List<ComicSummaryDto>,
        dates: List<ComicDateDto>,
        prices: List<ComicPriceDto>,
        thumbnail: ImageDto,
        images: List<ImageDto>,
        creators: CreatorsResourceListDto,
        characters: CharactersResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto
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
        textObjects: List<TextObjectDto>,
        urls: List<UrlDto>,
        series: SeriesSummaryDto,
        variants: List<ComicSummaryDto>,
        dates: List<ComicDateDto>,
        prices: List<ComicPriceDto>,
        thumbnail: ImageDto,
        images: List<ImageDto>,
        creators: CreatorsResourceListDto,
        characters: CharactersResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto
    )

    fun clearComics()
    fun clearComicsResource(resourceType: ResourceType, resourceId: Int)
}

class ComicLocalDataSourceImpl(
    db: MarvelUniverseDatabase
) : ComicLocalDataSource {
    private val comicQueries = db.comicQueries
    private val comicResourceQueries = db.comic_resourceQueries

    override fun getComics(): Flow<List<ComicEntity>> {
        return comicQueries
            .getComics()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }


    override fun getComicById(id: Int): ComicEntity? {
        return comicQueries
            .getComicById(id)
            .executeAsOneOrNull()
    }

    override fun getComicsByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<ComicResourceEntity>> {
        return comicResourceQueries
            .getComicsResourceByType(resourceType, resourceId)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override fun insertComic(
        id: Int,
        title: String,
        description: String,
        modified: LocalDateTime,
        isbn: String,
        pageCount: Int,
        textObjects: List<TextObjectDto>,
        urls: List<UrlDto>,
        series: SeriesSummaryDto,
        variants: List<ComicSummaryDto>,
        dates: List<ComicDateDto>,
        prices: List<ComicPriceDto>,
        thumbnail: ImageDto,
        images: List<ImageDto>,
        creators: CreatorsResourceListDto,
        characters: CharactersResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto
    ) {
        comicQueries
            .insertComic(
                id = id,
                title = title,
                description = description,
                modified = modified,
                isbn = isbn,
                pageCount = pageCount,
                textObjects = textObjects,
                urls = urls,
                series = series,
                variants = variants,
                dates = dates,
                prices = prices,
                thumbnail = thumbnail,
                images = images,
                creators = creators,
                characters = characters,
                stories = stories,
                events = events
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
        textObjects: List<TextObjectDto>,
        urls: List<UrlDto>,
        series: SeriesSummaryDto,
        variants: List<ComicSummaryDto>,
        dates: List<ComicDateDto>,
        prices: List<ComicPriceDto>,
        thumbnail: ImageDto,
        images: List<ImageDto>,
        creators: CreatorsResourceListDto,
        characters: CharactersResourceListDto,
        stories: StoriesResourceListDto,
        events: EventsResourceListDto
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
                textObjects = textObjects,
                urls = urls,
                series = series,
                variants = variants,
                dates = dates,
                prices = prices,
                thumbnail = thumbnail,
                images = images,
                creators = creators,
                characters = characters,
                stories = stories,
                events = events
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