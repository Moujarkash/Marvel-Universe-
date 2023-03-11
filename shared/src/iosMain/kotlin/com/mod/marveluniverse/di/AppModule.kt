package com.mod.marveluniverse.di

import com.mod.marveluniverse.data.config.DatabaseDriverFactory
import com.mod.marveluniverse.data.config.HttpClientFactory
import com.mod.marveluniverse.data.data_sources.local.*
import com.mod.marveluniverse.data.data_sources.remote.*
import com.mod.marveluniverse.data.repositories.*
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.repositories.*
import com.mod.marveluniverse.presentation.comic.list.ComicListViewModel

class AppModule {
    private val appDatabase: MarvelUniverseDatabase by lazy {
        SharedAppModule.provideAppDatabase(DatabaseDriverFactory().create())
    }

    val requestLocalDataSource: RequestLocalDataSource by lazy {
        RequestLocalDataSourceImpl(
            db = appDatabase
        )
    }

    val comicLocalDataSource: ComicLocalDataSource by lazy {
        ComicLocalDataSourceImpl(
            db = appDatabase
        )
    }

    val characterLocalDataSource: CharacterLocalDataSource by lazy {
        CharacterLocalDataSourceImpl(
            db = appDatabase
        )
    }

    val creatorLocalDataSource: CreatorLocalDataSource by lazy {
        CreatorLocalDataSourceImpl(
            db = appDatabase
        )
    }

    val eventLocalDataSource: EventLocalDataSource by lazy {
        EventLocalDataSourceImpl(
            db = appDatabase
        )
    }

    val seriesLocalDataSource: SeriesLocalDataSource by lazy {
        SeriesLocalDataSourceImpl(
            db = appDatabase
        )
    }

    val storyLocalDataSource: StoryLocalDataSource by lazy {
        StoryLocalDataSourceImpl(
            db = appDatabase
        )
    }

    val comicRemoteDataSource: ComicRemoteDataSource by lazy {
        ComicRemoteDataSourceImpl(
            httpClient = HttpClientFactory().create()
        )
    }

    val characterRemoteDataSource: CharacterRemoteDataSource by lazy {
        CharacterRemoteDataSourceImpl(
            httpClient = HttpClientFactory().create()
        )
    }

    val creatorRemoteDataSource: CreatorRemoteDataSource by lazy {
        CreatorRemoteDataSourceImpl(
            httpClient = HttpClientFactory().create()
        )
    }

    val eventRemoteDataSource: EventRemoteDataSource by lazy {
        EventRemoteDataSourceImpl(
            httpClient = HttpClientFactory().create()
        )
    }

    val seriesRemoteDataSource: SeriesRemoteDataSource by lazy {
        SeriesRemoteDataSourceImpl(
            httpClient = HttpClientFactory().create()
        )
    }

    val storyRemoteDataSource: StoryRemoteDataSource by lazy {
        StoryRemoteDataSourceImpl(
            httpClient = HttpClientFactory().create()
        )
    }

    val comicRepository: ComicRepository by lazy {
        ComicRepositoryImpl(
            requestLocalDataSource, comicLocalDataSource, comicRemoteDataSource
        )
    }

    val characterRepository: CharacterRepository by lazy {
        CharacterRepositoryImpl(
            requestLocalDataSource, characterLocalDataSource, characterRemoteDataSource
        )
    }

    val creatorRepository: CreatorRepository by lazy {
        CreatorRepositoryImpl(
            requestLocalDataSource, creatorLocalDataSource, creatorRemoteDataSource
        )
    }

    val eventRepository: EventRepository by lazy {
        EventRepositoryImpl(
            requestLocalDataSource, eventLocalDataSource, eventRemoteDataSource
        )
    }

    val seriesRepository: SeriesRepository by lazy {
        SeriesRepositoryImpl(
            requestLocalDataSource, seriesLocalDataSource, seriesRemoteDataSource
        )
    }

    val storyRepository: StoryRepository by lazy {
        StoryRepositoryImpl(
            requestLocalDataSource, storyLocalDataSource, storyRemoteDataSource
        )
    }

    val comicListViewModel: ComicListViewModel by lazy {
        ComicListViewModel(
            comicRepository, null
        )
    }
}