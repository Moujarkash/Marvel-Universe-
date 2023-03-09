package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.dtos.*
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.entites.ResourceType
import database.comic.ComicEntity
import database.comic.ComicResourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ComicLocalDataSource {
    suspend fun getComics(): Flow<List<ComicEntity>>
    suspend fun getComicsByRequestCode(requestCode: String): Flow<List<ComicEntity>>
    suspend fun getComicById(id: Int): ComicEntity?
    suspend fun getComicsByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<ComicResourceEntity>>

    suspend fun insertComics(requestCode: String, comics: List<ComicDto>)

    suspend fun insertComicsResource(
        resourceType: ResourceType,
        resourceId: Int,
        comics: List<ComicDto>
    )

    suspend fun clearComicsByRequestCode(requestCode: String)
    suspend fun clearComicsByRemoteIdsAndRequestCode(remoteIds: List<Int>, requestCode: String)
    suspend fun clearComicsResource(resourceType: ResourceType, resourceId: Int)
}

class ComicLocalDataSourceImpl(
    db: MarvelUniverseDatabase
) : ComicLocalDataSource {
    private val comicQueries = db.comicQueries
    private val comicResourceQueries = db.comic_resourceQueries

    override suspend fun getComics(): Flow<List<ComicEntity>> {
        return comicQueries
            .getComics()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override suspend fun getComicsByRequestCode(requestCode: String): Flow<List<ComicEntity>> {
        return comicQueries
            .getComicsByRequestCode(requestCode)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }


    override suspend fun getComicById(id: Int): ComicEntity? {
        return comicQueries
            .getComicByRemoteId(id)
            .executeAsOneOrNull()
    }

    override suspend fun getComicsByResource(
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

    override suspend fun insertComics(requestCode: String, comics: List<ComicDto>) {
        comicQueries.transaction {
            comics.forEach {
                comicQueries
                    .insertComic(
                        id = null,
                        requestCode = requestCode,
                        remoteId = it.id,
                        title = it.title,
                        description = it.description,
                        modified = it.modified,
                        isbn = it.isbn,
                        pageCount = it.pageCount,
                        textObjects = it.textObjects,
                        urls = it.urls,
                        series = it.series,
                        variants = it.variants,
                        dates = it.dates,
                        prices = it.prices,
                        thumbnail = it.thumbnail,
                        images = it.images,
                        creators = it.creators,
                        characters = it.characters,
                        stories = it.stories,
                        events = it.events
                    )
            }
        }
    }

    override suspend fun insertComicsResource(
        resourceType: ResourceType,
        resourceId: Int,
        comics: List<ComicDto>
    ) {
        comicResourceQueries.transaction {
            comics.forEach {
                comicResourceQueries
                    .insertComicResource(
                        id = null,
                        resourceType = resourceType,
                        resourceId = resourceId,
                        remoteId = it.id,
                        title = it.title,
                        description = it.description,
                        modified = it.modified,
                        isbn = it.isbn,
                        pageCount = it.pageCount,
                        textObjects = it.textObjects,
                        urls = it.urls,
                        series = it.series,
                        variants = it.variants,
                        dates = it.dates,
                        prices = it.prices,
                        thumbnail = it.thumbnail,
                        images = it.images,
                        creators = it.creators,
                        characters = it.characters,
                        stories = it.stories,
                        events = it.events
                    )
            }
        }

    }

    override suspend fun clearComicsByRequestCode(requestCode: String) {
        comicQueries.clearRequestComics(requestCode)
    }

    override suspend fun clearComicsByRemoteIdsAndRequestCode(
        remoteIds: List<Int>,
        requestCode: String
    ) {
        comicQueries.clearComicsByRemoteIdsAndRequestCode(remoteIds, requestCode)
    }

    override suspend fun clearComicsResource(resourceType: ResourceType, resourceId: Int) {
        comicResourceQueries.clearComicsResource(
            resourceType = resourceType,
            resourceId = resourceId
        )
    }
}